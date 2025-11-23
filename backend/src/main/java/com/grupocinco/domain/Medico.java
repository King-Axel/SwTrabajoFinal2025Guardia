package com.grupocinco.domain;

import lombok.Getter;

@Getter
public class Medico extends Persona {
    private final String matricula;

    public Medico(String apellido, String nombre, String matricula) {
        super(apellido, nombre);

        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("La matricula no puede estar vacía");

        this.matricula = matricula;
    }
}
