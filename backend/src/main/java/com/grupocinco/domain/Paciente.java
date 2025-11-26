package com.grupocinco.domain;

import lombok.Getter;

@Getter
public class Paciente extends Persona {
    private Domicilio domicilio;
    private Afiliado afiliado;

    public Paciente(String apellido, String nombre, String cuil) {
        super(apellido, nombre, cuil);
    }

    public Paciente(String cuil, String apellido, String nombre, Domicilio domicilio, Afiliado afiliado) {
        super(cuil, apellido, nombre);
        this.afiliado = afiliado;
        this.domicilio = domicilio;
    }
}
