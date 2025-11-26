package com.grupocinco.app.controllers;

import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.app.interfaces.RepositorioPersonal;
import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.services.ServicioCuentas;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Ingreso;

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
                    req.getPaciente().getApellido(),
                    req.getPaciente().getNombre(),
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
            return ResponseEntity.badRequest().body(new Mensaje(e.getMessage()));
        }
    }

    static class Mensaje {
        public String mensaje;
        public Mensaje(String mensaje) { this.mensaje = mensaje; }
    }
}