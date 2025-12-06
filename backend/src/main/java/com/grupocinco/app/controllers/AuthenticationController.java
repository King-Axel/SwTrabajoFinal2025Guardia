package com.grupocinco.app.controllers;

import com.grupocinco.app.ServicioAutenticacion;
import com.grupocinco.app.dtos.PersonaDTO;
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
    private final ServicioAutenticacion servicioAutenticacion;
    private final ServicioPersonal servicioPersonal;
    private final JwtUtil jwtUtil;

    public AuthenticationController(ServicioAutenticacion servicioAutenticacion, ServicioPersonal servicioPersonal,
            JwtUtil jwtUtil) {
        this.servicioAutenticacion = servicioAutenticacion;
        this.servicioPersonal = servicioPersonal;
        this.jwtUtil = jwtUtil;
    }

    public record LoginRequest(String email, String password) {
    }

    public record RegisterRequest(String email, String password, String rol, String cuil) {
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            Cuenta cuenta = servicioAutenticacion.iniciarSesion(credentials.email(), credentials.password());

            Map<String, Object> claims = new HashMap<>();
            claims.put("rol", cuenta.getRol().name());
            claims.put("persona", cuenta.getPersona());
            String token = jwtUtil.generateToken(cuenta.getEmail(), claims);
            respuesta.put("token", token);
            respuesta.put("rol", cuenta.getRol());
            respuesta.put("mensaje", "Inicio de sesión exitoso");
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            servicioAutenticacion.registrar(registerRequest.email(), registerRequest.password(), registerRequest.rol(),
                    registerRequest.cuil());
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
