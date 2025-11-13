package com.grupocinco.domain;

public abstract class Persona {
    private  String apellido;
    private String nombre;

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona(String apellido, String nombre) {
        this.apellido = apellido;
        this.nombre = nombre;
    }
}
