package com.grupocinco.app.controllers;

import com.grupocinco.app.dtos.AtencionDTO;
import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.app.interfaces.IRepositorioPersonal;
import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.mappers.IngresoMapper;
import com.grupocinco.app.mappers.PersonaMapper;
import com.grupocinco.app.services.ServicioCuentas;
import com.grupocinco.app.services.ServicioPacientes;
import com.grupocinco.app.services.ServicioPersonal;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Ingreso;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Paciente;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .ok(servicioUrgencias.obtenerIngresosEnEspera().stream().map(IngresoMapper::aDTO).toList());
    }

    @PreAuthorize("hasAuthority('PERM_IS202501_REGISTRO_ADMISION')")
    @PostMapping("/ingresos")
    public ResponseEntity<?> registrarIngreso(@Valid @RequestBody IngresoDTO req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emaillUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emaillUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String cuilUsuario = cuenta.getPersona().getCuil();

            Enfermera enfermera = servicioPersonal.listarPersonal().stream()
                    .filter(p -> p.getRol().equals(Rol.ENFERMERA.name()))
                    .map(p -> (Enfermera) PersonaMapper.desdeDTO(p))
                    .filter(e -> e.getCuil().equals(cuilUsuario))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró la enfermera logueada"));

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

            return ResponseEntity.ok(new Mensaje("Ingreso registrado correctamente"));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? "Error al registrar ingreso"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(new Mensaje(msg));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')")
    @PostMapping("/reclamos/proximo")
    public ResponseEntity<?> reclamarProximo() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emailUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String cuilUsuario = cuenta.getPersona().getCuil();

            Medico medico = servicioPersonal.listarPersonal().stream()
                    .filter(p -> p.getRol().equals(Rol.MEDICO.name()))
                    .map(p -> (Medico) PersonaMapper.desdeDTO(p))
                    .filter(m -> m.getCuil().equals(cuilUsuario))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró el médico logueado"));

            Ingreso reclamado = servicioUrgencias.reclamarProximoIngreso(medico);
            return ResponseEntity.ok(IngresoMapper.aDTO(reclamado));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank())
                    ? "Error al reclamar próximo ingreso"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(new Mensaje(msg));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')") 
    @GetMapping("/en-proceso")
    public ResponseEntity<List<IngresoDTO>> obtenerIngresosEnProceso() {
        List<IngresoDTO> dtos = servicioUrgencias.obtenerIngresosEnAtencion()
                .stream()
                .map(IngresoMapper::aDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('PERM_IS202503_RECLAMO_PACIENTE')")
    @GetMapping("/atencion/historial")
    public ResponseEntity<List<IngresoDTO>> obtenerHistorialAtencion() {
        List<IngresoDTO> dtos = servicioUrgencias.obtenerIngresosEnAtencion()
                .stream()
                .map(IngresoMapper::aDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('PERM_IS202504_REGISTRO_ATENCION')")
    @PostMapping("/atencion")
    public ResponseEntity<?> registrarAtencion(@Valid @RequestBody AtencionDTO req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emaillUsuario = auth.getName();

            Cuenta cuenta = servicioCuentas.buscarPorEmail(emaillUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String cuilUsuario = cuenta.getPersona().getCuil();

            Medico medico = servicioPersonal.listarPersonal().stream()
                    .filter(p -> p.getRol().equals(Rol.MEDICO.name()))
                    .map(p -> (Medico) PersonaMapper.desdeDTO(p))
                    .filter(m -> m.getCuil().equals(cuilUsuario))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró el médico logueado"));

            servicioUrgencias.registrarAtencion(
                    req.getIngreso().getId(),
                    medico,
                    req.getInforme());

            return ResponseEntity.ok(new Mensaje("Atencion registrada correctamente"));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? "Error al registrar la atencion"
                    : e.getMessage();
            return ResponseEntity.badRequest().body(new Mensaje(msg));
        }
    }

    static class Mensaje {
        public String mensaje;

        public Mensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}