package com.grupocinco.domain;

import lombok.Getter;

@Getter
public abstract class Persona {
    private final String apellido;
    private final String nombre;

    protected Persona(String apellido, String nombre) {
        if (apellido == null || apellido.isBlank()) throw new IllegalArgumentException("El apellido no puede estar vacío");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío");

        this.apellido = apellido;
        this.nombre = nombre;
    }
}
