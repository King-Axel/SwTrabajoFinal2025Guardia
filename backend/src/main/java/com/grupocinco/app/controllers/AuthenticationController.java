package com.grupocinco.app.controllers;

import com.grupocinco.app.ServicioAutenticacion;
import com.grupocinco.app.dtos.CuentaDTO;
import com.grupocinco.app.dtos.LoginDTO;
import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.repository.RepositorioDePersonal;
import com.grupocinco.app.services.ServicioPersonal;
import com.grupocinco.app.util.JwtUtil;
import com.grupocinco.domain.Cuenta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final ServicioAutenticacion servicio;
    private final ServicioPersonal servicioPersonal;

    // Inyeccion de dependencia
    public AuthenticationController(ServicioAutenticacion servicio, ServicioPersonal servicioPersonal) {
        this.servicio = servicio;
        this.servicioPersonal = servicioPersonal;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            Cuenta cuenta = servicio.iniciarSesionDTO(dto);

            Map<String, Object> claims = new HashMap<>();
            claims.put("rol", cuenta.getRol());
            claims.put("persona", cuenta.getPersona());
            String token = JwtUtil.generateToken(cuenta.getEmail(), claims);
            respuesta.put("token", token);
            respuesta.put("mensaje", "Inicio de sesión exitoso");
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody CuentaDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            servicio.registrarDTO(dto);
            respuesta.put("mensaje", "Registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/personal")
    public List<PersonaDTO> getPersonas() {
        return servicioPersonal.listarPersonal();
    }
}
