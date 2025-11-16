package com.grupocinco.app;

import com.grupocinco.app.dtos.CuentaDTO;
import com.grupocinco.app.dtos.LoginDTO;
import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.exceptions.CredencialesInvalidasException;
import com.grupocinco.app.exceptions.CuentaExistenteException;
import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.app.security.PasswordHasher;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioAutenticacion {
    private RepositorioCuentas dbCuentas;

    // Inyeccion de dependencia
    public ServicioAutenticacion(RepositorioCuentas dbCuentas) {
        this.dbCuentas = dbCuentas;
    }

    // Para uso real
    public boolean registrarDTO(CuentaDTO dto) {
        Cuenta cuenta = dto.aClase();

        if(dbCuentas.buscar(cuenta.getEmail()).isPresent()) {
            throw new CuentaExistenteException();
        } else {
            return dbCuentas.guardar(cuenta);
        }
    }

    // Para pruebas
    public boolean registrar(Cuenta cuenta) {
        Cuenta existencia = dbCuentas.buscar(cuenta.getEmail()).orElse(null);

        if (existencia != null) throw new CuentaExistenteException();

        return dbCuentas.guardar(cuenta);
    }

    // Para uso real
    public Cuenta iniciarSesionDTO(LoginDTO dto) {
        Email email = Email.of(dto.getEmail());
        Contrasena contrasena = Contrasena.fromRaw(dto.getContrasena());

        Cuenta existencia = dbCuentas.buscar(dto.getEmail()).orElse(null);

        if (
                existencia == null ||
                !PasswordHasher.matches(dto.getContrasena(), existencia.getContrasena())
        ) throw new CredencialesInvalidasException();

        return existencia;
    }

    // Para pruebas
    public boolean iniciarSesion(String email, String contrasenaRaw) {
        Cuenta cuenta = dbCuentas.buscar(email)
                .orElse(null);

        if (cuenta == null || !PasswordHasher.matches(contrasenaRaw, cuenta.getContrasena())) {
            throw new CredencialesInvalidasException();
        }

        return true;
    }
}
