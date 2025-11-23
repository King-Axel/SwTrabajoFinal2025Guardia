package com.grupocinco.domain;

import lombok.Getter;

@Getter
public abstract class Persona {
    private final String apellido;
    private final String nombre;
    private final String cuil;

    protected Persona(String apellido, String nombre, String cuil) {
        if (apellido == null || apellido.isBlank()) throw new IllegalArgumentException("El apellido no puede estar vacío");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (cuil == null || cuil.isBlank()) throw new IllegalArgumentException("El cuil no puede estar vacío");

        this.apellido = apellido;
        this.nombre = nombre;
        this.cuil = cuil;
    }
}
