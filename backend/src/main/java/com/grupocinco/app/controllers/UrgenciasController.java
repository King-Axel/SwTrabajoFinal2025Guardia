package com.grupocinco.app.controllers;

import com.grupocinco.app.dtos.IngresoUrgenciaRequest;
import com.grupocinco.app.interfaces.RepositorioPersonal;
import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urgencias")
public class UrgenciasController {
    private ServicioUrgencias servicioUrgencias;
    private RepositorioPersonal repoPersonal;

    public UrgenciasController(ServicioUrgencias servicioUrgencias, RepositorioPersonal repoPersonal) {
        this.servicioUrgencias = servicioUrgencias;
        this.repoPersonal = repoPersonal;
    }

    @GetMapping("/espera")
    public ResponseEntity<List<Ingreso>> obtenerIngresosEnEspera() {
        return ResponseEntity.ok(servicioUrgencias.obtenerIngresosEnEspera());
    }


    @PostMapping("/ingresos")
    public ResponseEntity<?> registrarIngreso(@RequestBody IngresoUrgenciaRequest req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String cuilUsuario = auth.getName();

            Enfermera enfermera = repoPersonal.findAll().stream()
                    .filter(p -> p instanceof Enfermera)
                    .map(p -> (Enfermera) p)
                    .filter(e -> e.getCuil().equals(cuilUsuario))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró la enfermera logueada"));

            servicioUrgencias.registrarIngreso(
                    req.getCuil(),
                    req.getApellido(),
                    req.getNombre(),
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