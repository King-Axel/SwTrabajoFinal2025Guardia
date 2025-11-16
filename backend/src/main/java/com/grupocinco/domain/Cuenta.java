package com.grupocinco.domain;

import com.grupocinco.app.exceptions.InconsistenciaRolPersonalException;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;

public class Cuenta {
    private Email email;
    private Contrasena contrasenaHash;
    private Rol rol;
    private Persona persona;

    public Cuenta(Email email, Contrasena contrasenaHash, Rol rol, Persona persona) {
        validarRolYPersonalAsociado(rol, persona);
        this.email = email;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
        this.persona = persona;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public Rol getRol() {
        return rol;
    }

    public String getContrasena() {
        return contrasenaHash.get();
    }

    public Persona getPersona() {
        return persona;
    }

    public void validarRolYPersonalAsociado(Rol rol, Persona persona) {
        if (
                rol == Rol.ENFERMERA && !(persona instanceof Enfermera) ||
                rol == Rol.MEDICO && !(persona instanceof Medico)
        )
            throw new InconsistenciaRolPersonalException(rol, persona);
    }

    // FACTORIAS DE CUENTAS -- Pero se pierde control sobre el error
    /*
    public static Cuenta crearCuentaMedico(Email email, Contrasena contrasenaHash, Medico medico) {
        return new Cuenta(email, contrasenaHash, Rol.MEDICO, medico);
    }
    public static Cuenta crearCuentaEnfermera(Email email, Contrasena contrasenaHash, Enfermera enfermera) {
        return new Cuenta(email, contrasenaHash, Rol.ENFERMERA, enfermera);
    }
    */
}
