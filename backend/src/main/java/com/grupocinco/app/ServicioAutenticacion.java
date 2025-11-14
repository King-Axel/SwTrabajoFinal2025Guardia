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
import com.grupocinco.domain.Persona;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;

public class ServicioAutenticacion {
    private RepositorioCuentas dbCuentas;

    // Inyeccion de dependencia
    public ServicioAutenticacion(RepositorioCuentas dbCuentas) {
        this.dbCuentas = dbCuentas;
    }

    /*
    * PARA CUANDO SE IMPLEMENTE CON VISTA Y CONTROLADOR
    *
    public boolean registrar(CuentaDTO cuentaDTO) {
        PersonaDTO personaDTO = cuentaDTO.getPersona();
        Persona persona =

        Cuenta cuenta =
                new Cuenta(
                        Email.of(cuentaDTO.getEmail()),
                        Contrasena.fromRaw(cuentaDTO.getContrasena()),
                        cuentaDTO.getRol(),
                        cuentaDTO.getPersona());

        return dbCuentas.guardar(cuenta);
    }

    public boolean iniciarSesion(LoginDTO loginDTO) {
        return dbCuentas.buscar(loginDTO.getEmail())
                .map(
                        cuenta -> PasswordHasher.matches(loginDTO.getContrasena(), cuenta.getContrasena().get())
                )
                .orElse(false);
                //.orElseThrow(new CredencialesInvalidasException("Usuario o contraseña inválidos"));
    }
     */

    public boolean registrar(Cuenta cuenta) {
        Cuenta existencia = dbCuentas.buscar(cuenta.getEmail()).orElse(null);

        if (existencia != null) throw new CuentaExistenteException();

        return dbCuentas.guardar(cuenta);
    }

    public boolean iniciarSesion(String email, String contrasenaRaw) {
        Cuenta cuenta = dbCuentas.buscar(email)
                .orElse(null);

        if (cuenta == null || !PasswordHasher.matches(contrasenaRaw, cuenta.getContrasena().get())) {
            throw new CredencialesInvalidasException();
        }

        return true;
    }
}
