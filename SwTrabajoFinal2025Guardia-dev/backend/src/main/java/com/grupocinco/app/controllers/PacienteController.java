package com.grupocinco.app.controllers;

import com.grupocinco.app.dtos.PacienteDTO;
import com.grupocinco.app.ServicioRegistrarPaciente;
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

    public PacienteController(ServicioRegistrarPaciente servicioRegistrarPaciente) {
        this.servicioRegistrarPaciente = servicioRegistrarPaciente;
    }

    @PreAuthorize("hasAuthority('PERM_IS202502_REGISTRO_PACIENTE')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PacienteDTO dto) {
        try {
            servicioRegistrarPaciente.registrarPaciente(dto); // <- ajustamos firma abajo
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

}

