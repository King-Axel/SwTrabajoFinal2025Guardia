package com.grupocinco.app.controllers;

import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.app.interfaces.RepositorioPersonal;
import com.grupocinco.app.ServicioRegistrarPaciente;
import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.services.ServicioCuentas;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Ingreso;
import com.grupocinco.domain.Paciente;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urgencias")
public class UrgenciasController {
    private final ServicioUrgencias servicioUrgencias;
    private final RepositorioPersonal repoPersonal;
    private final ServicioCuentas servicioCuentas;

    public UrgenciasController(ServicioUrgencias servicioUrgencias, RepositorioPersonal repoPersonal, ServicioCuentas servicioCuentas) {
        this.servicioUrgencias = servicioUrgencias;
        this.repoPersonal = repoPersonal;
        this.servicioCuentas = servicioCuentas;
    }

    @GetMapping("/espera")
    public ResponseEntity<List<Ingreso>> obtenerIngresosEnEspera() {
        return ResponseEntity.ok(servicioUrgencias.obtenerIngresosEnEspera());
    }

    @PostMapping("/ingresos")
    public ResponseEntity<?> registrarIngreso(@RequestBody IngresoDTO req) {
        try {
            if (req == null) return ResponseEntity.badRequest().body(new Mensaje("Faltan los datos del ingreso"));
            if (req.getPaciente() == null) return ResponseEntity.badRequest().body(new Mensaje("Faltan los datos del paciente"));
            if (req.getPaciente().getCuil() == null || req.getPaciente().getCuil().isBlank())
                return ResponseEntity.badRequest().body(new Mensaje("Falta el dato CUIL"));

            if (req.getInforme() == null || req.getInforme().isBlank())
                throw new IllegalArgumentException("Falta el dato Informe");
            if (req.getNivelEmergencia() == null || req.getNivelEmergencia().isBlank())
                throw new IllegalArgumentException("Falta el dato Nivel de emergencia");

            // Mandatorios numéricos (evita el famoso "null")
            if (req.getTemperatura() == null) throw new IllegalArgumentException("Falta el dato Temperatura");
            if (req.getFrecuenciaCardiaca() == null) throw new IllegalArgumentException("Falta el dato Frecuencia cardiaca");
            if (req.getFrecuenciaRespiratoria() == null) throw new IllegalArgumentException("Falta el dato Frecuencia respiratoria");
            if (req.getFrecuenciaSistolica() == null) throw new IllegalArgumentException("Falta el dato Presion sistolica");
            if (req.getFrecuenciaDiastolica() == null) throw new IllegalArgumentException("Falta el dato Presion diastolica");

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emaillUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emaillUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String cuilUsuario = cuenta.getPersona().getCuil();

            Enfermera enfermera = repoPersonal.findAll().stream()
                    .filter(p -> p instanceof Enfermera)
                    .map(p -> (Enfermera) p)
                    .filter(e -> e.getCuil().equals(cuilUsuario))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró la enfermera logueada"));

            servicioUrgencias.registrarIngreso(
                    req.getPaciente().getCuil(),
                    //req.getPaciente().getApellido(),
                    //req.getPaciente().getNombre(),
                    enfermera,
                    req.getInforme(),
                    req.getNivelEmergencia(),
                    String.valueOf(req.getTemperatura()),
                    String.valueOf(req.getFrecuenciaCardiaca()),
                    String.valueOf(req.getFrecuenciaRespiratoria()),
                    String.valueOf(req.getFrecuenciaSistolica()),
                    String.valueOf(req.getFrecuenciaDiastolica())
            );

            return ResponseEntity.ok(new Mensaje("Ingreso registrado correctamente"));

        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? "Error al registrar ingreso" : e.getMessage();
            return ResponseEntity.badRequest().body(new Mensaje(msg));
        }
    }

    static class Mensaje {
        public String mensaje;
        public Mensaje(String mensaje) { this.mensaje = mensaje; }
    }
}