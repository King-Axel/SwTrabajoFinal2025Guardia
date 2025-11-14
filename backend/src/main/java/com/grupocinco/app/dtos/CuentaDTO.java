package com.grupocinco.app.dtos;

import com.grupocinco.app.util.Rol;

public class CuentaDTO {
    private String email;
    private String contrasena;
    private String rol;
    private PersonaDTO persona;

    public CuentaDTO(String email, String contrasena, String rol, PersonaDTO persona) {
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
        this.persona = persona;
    }

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
}
