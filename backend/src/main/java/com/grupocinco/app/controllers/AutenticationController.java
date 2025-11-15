package com.grupocinco.app.controllers;

import com.grupocinco.app.ServicioAutenticacion;
import com.grupocinco.app.dtos.CuentaDTO;
import com.grupocinco.app.dtos.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AutenticationController {
    private final ServicioAutenticacion servicio;

    // Inyeccion de dependencia
    public AutenticationController(ServicioAutenticacion servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        boolean ok = servicio.iniciarSesion(login);

        if (ok) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Usuario o contraseña inválidos"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CuentaDTO cuenta) {
        /*if (servicio.registrar(cuenta)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensaje", "Error al registrar la cuenta"));*/
        return ResponseEntity.ok().build();
    }
}
