package com.grupocinco.app.dtos;

import com.grupocinco.app.exceptions.RolInvalidoException;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;

import java.util.Arrays;

public class CuentaDTO {
    private String email;
    private String contrasena;
    private String rol;
    private PersonaDTO persona;

    public CuentaDTO() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public PersonaDTO getPersona() {
        return persona;
    }
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    public Cuenta aClase() {
        Persona persona1;
        Rol rol1 = Rol.desdeString(rol);

        if (rol1 == Rol.MEDICO) {
            persona1 = new Medico(persona.getApellido(), persona.getNombre(), persona.getMatricula());
        } else {
            persona1 = new Enfermera(persona.getApellido(), persona.getNombre());
        }

        return new Cuenta(
                Email.of(email), Contrasena.fromRaw(contrasena), rol1, persona1
        );
    }
}
