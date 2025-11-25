package com.grupocinco.domain;

import lombok.Getter;

@Getter
public abstract class Persona {
    private final String apellido;
    private final String nombre;
    private final String cuil;

    protected Persona(String apellido, String nombre, String cuil) {
        if (apellido == null || apellido.isBlank()) throw new IllegalArgumentException("Falta el dato Apellido");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Falta el dato Nombre");
        if (cuil == null || cuil.isBlank()) throw new IllegalArgumentException("Falta el dato Cuil");

        this.apellido = apellido;
        this.nombre = nombre;
        this.cuil = cuil;
    }
}
