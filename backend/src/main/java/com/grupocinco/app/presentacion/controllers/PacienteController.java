package com.grupocinco.app.presentacion.controllers;

import com.grupocinco.app.infraestructura.dtos.PacienteDTO;
import com.grupocinco.app.services.casouso.ServicioRegistrarPaciente;
import com.grupocinco.app.services.entidad.ServicioObrasSociales;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final ServicioRegistrarPaciente servicioRegistrarPaciente;
    private final ServicioObrasSociales servicioObrasSociales;

    public PacienteController(ServicioRegistrarPaciente servicioRegistrarPaciente, ServicioObrasSociales servicioObrasSociales) {
        this.servicioRegistrarPaciente = servicioRegistrarPaciente;
        this.servicioObrasSociales = servicioObrasSociales;
    }

    @PreAuthorize("hasAuthority('PERM_IS202501_REGISTRO_ADMISION')")
    @GetMapping("/")
    public ResponseEntity<?> obtenerTodos() {
        try {
            return ResponseEntity.ok(servicioRegistrarPaciente.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202502_REGISTRO_PACIENTE')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PacienteDTO dto) {
        try {
            System.out.println("Recibido: " + dto.toString());
            servicioRegistrarPaciente.registrarPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Paciente creado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202502_REGISTRO_PACIENTE')")
    @GetMapping("/{cuil}")
    public ResponseEntity<?> buscarPorCuil(@PathVariable String cuil) {
        try {
            return servicioRegistrarPaciente.buscarPorCuil(cuil)
                    .<ResponseEntity<?>>map(p -> ResponseEntity.ok(p))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("mensaje", "Paciente no existe")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('PERM_IS202502_REGISTRO_PACIENTE')")
    @GetMapping("/obrassociales")
    public ResponseEntity<?> obtenerTodasObrassociales() {
        try {
            return ResponseEntity.ok(servicioObrasSociales.obtenerTodas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }
}

