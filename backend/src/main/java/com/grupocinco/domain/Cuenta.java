package com.grupocinco.domain;

import com.grupocinco.app.exceptions.InconsistenciaRolPersonalException;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import lombok.Getter;

@Getter
public class Cuenta {
    private final Persona persona;
    private final Email email;
    private final Contrasena contrasena;
    private final Rol rol;

    public Cuenta(Email email, Contrasena contrasena, Rol rol, Persona persona) {
        validarRolYPersonalAsociado(rol, persona);
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
        this.persona = persona;
    }

    public String getEmail() {
        return email.get();
    }

    public String getContrasena() {
        return contrasena.get();
    }

    public void validarRolYPersonalAsociado(Rol rol, Persona persona) {
        if (
                rol == Rol.ENFERMERA && !(persona instanceof Enfermera) ||
                rol == Rol.MEDICO && !(persona instanceof Medico)
        )
            throw new InconsistenciaRolPersonalException(rol, persona);
    }
}
