package com.grupocinco.domain;

import lombok.Getter;

@Getter
public class Paciente extends Persona {
    public Paciente(String apellido, String nombre, String cuil) {
        super(apellido, nombre, cuil);
    }
}
