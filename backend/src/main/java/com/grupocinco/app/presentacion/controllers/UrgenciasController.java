package com.grupocinco.app.presentacion.controllers;

import com.grupocinco.app.infraestructura.dtos.IngresoDTO;
import com.grupocinco.app.services.casouso.ServicioUrgencias;
import com.grupocinco.app.services.entidad.ServicioCuentas;
import com.grupocinco.app.services.entidad.ServicioPersonal;
import com.grupocinco.dominio.Cuenta;
import com.grupocinco.dominio.Enfermera;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urgencias")
public class UrgenciasController {
    private final ServicioUrgencias servicioUrgencias;
    private final ServicioPersonal servicioPersonal;
    private final ServicioCuentas servicioCuentas;

    public UrgenciasController(
            ServicioUrgencias servicioUrgencias, ServicioPersonal servicioPersonal, ServicioCuentas servicioCuentas) {
        this.servicioUrgencias = servicioUrgencias;
        this.servicioPersonal = servicioPersonal;
        this.servicioCuentas = servicioCuentas;
    }

    @PreAuthorize("hasAuthority('PERM_IS202505_VER_COLA_ESPERA')")
    @GetMapping("/espera")
    public ResponseEntity<List<IngresoDTO>> obtenerIngresosEnEspera() {
        return ResponseEntity
                .ok(servicioUrgencias.obtenerIngresosEnEspera());
    }

    @PreAuthorize("hasAuthority('PERM_IS202501_REGISTRO_ADMISION')")
    @PostMapping("/ingresos")
    public ResponseEntity<?> registrarIngreso(@Valid @RequestBody IngresoDTO req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emaillUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emaillUsuario);
            String cuilUsuario = cuenta.getPersona().getCuil();

            Enfermera enfermera = servicioPersonal
                    .buscarPersonalPorCuilYRol(cuilUsuario, Enfermera.class);

            servicioUrgencias.registrarIngreso(
                    req.getPaciente().getCuil(),
                    enfermera,
                    req.getInforme(),
                    req.getNivelEmergencia(),
                    req.getTemperatura(),
                    req.getFrecuenciaCardiaca(),
                    req.getFrecuenciaRespiratoria(),
                    req.getFrecuenciaSistolica(),
                    req.getFrecuenciaDiastolica());

            return ResponseEntity.ok(Map.of("Mensaje", "Ingreso registrado correctamente"));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? "Error al registrar ingreso"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(Map.of("mensaje", msg));
        }
    }
}