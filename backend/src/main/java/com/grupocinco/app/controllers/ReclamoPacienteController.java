package com.grupocinco.app.controllers;

import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.app.mappers.IngresoMapper;
import com.grupocinco.app.services.ServicioCuentas;
import com.grupocinco.app.services.ServicioPersonal;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Ingreso;
import com.grupocinco.domain.Medico;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urgencias")
public class ReclamoPacienteController {
    private final ServicioCuentas servicioCuentas;
    private final ServicioPersonal servicioPersonal;
    private final ServicioUrgencias servicioUrgencias;

    public ReclamoPacienteController(ServicioCuentas servicioCuentas, ServicioPersonal servicioPersonal, ServicioUrgencias servicioUrgencias) {
        this.servicioCuentas = servicioCuentas;
        this.servicioPersonal = servicioPersonal;
        this.servicioUrgencias = servicioUrgencias;
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')")
    @PostMapping("/reclamos/proximo")
    public ResponseEntity<?> reclamarProximo() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emailUsuario);
            String cuilUsuario = cuenta.getPersona().getCuil();

            Medico medico = servicioPersonal
                    .buscarPersonalPorCuilYRol(cuilUsuario, Medico.class);

            Ingreso reclamado = servicioUrgencias.reclamarProximoIngreso();
            return ResponseEntity.ok(IngresoMapper.aDTO(reclamado));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank())
                    ? "Error al reclamar próximo ingreso"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(Map.of("mensaje", msg));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')")
    @GetMapping("/en-proceso")
    public ResponseEntity<List<IngresoDTO>> obtenerIngresosEnProceso() {
        List<IngresoDTO> dtos = servicioUrgencias.obtenerIngresosEnAtencion();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')")
    @GetMapping("/atencion/historial")
    public ResponseEntity<?> obtenerHistorialAtencion() {
        try {
            List<IngresoDTO> dtos = servicioUrgencias.obtenerTodosNoPendientes();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }
}
