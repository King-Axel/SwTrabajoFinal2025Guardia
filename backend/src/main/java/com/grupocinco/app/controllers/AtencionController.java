package com.grupocinco.app.controllers;

import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.dtos.AtencionDTO;
import com.grupocinco.app.services.ServicioCuentas;
import com.grupocinco.app.services.ServicioPersonal;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Medico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/urgencias")
public class AtencionController {
    private final ServicioCuentas servicioCuentas;
    private final ServicioPersonal servicioPersonal;
    private final ServicioUrgencias servicioUrgencias;

    public AtencionController(ServicioCuentas servicioCuentas, ServicioPersonal servicioPersonal, ServicioUrgencias servicioUrgencias) {
        this.servicioCuentas = servicioCuentas;
        this.servicioPersonal = servicioPersonal;
        this.servicioUrgencias = servicioUrgencias;
    }

    @PreAuthorize("hasAuthority('PERM_IS202504_REGISTRO_ATENCION')")
    @PostMapping("/atencion")
    public ResponseEntity<?> registrarAtencion(@Valid @RequestBody AtencionDTO req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emaillUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emaillUsuario);
            String cuilUsuario = cuenta.getPersona().getCuil();

            Medico medico = servicioPersonal.buscarPersonalPorCuilYRol(cuilUsuario, Medico.class);

            servicioUrgencias.registrarAtencion(
                    req.getIngreso().getId(),
                    medico,
                    req.getInforme());

            return ResponseEntity.ok(Map.of("mensaje", "Atencion registrada correctamente"));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? "Error al registrar la atencion"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(Map.of("mensaje", msg));
        }
    }
}
