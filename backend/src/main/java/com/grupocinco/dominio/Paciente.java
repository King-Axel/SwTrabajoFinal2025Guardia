package com.grupocinco.dominio;

import lombok.Getter;

@Getter
public class Paciente extends Persona {
    private Domicilio domicilio;
    private Afiliado afiliado;

    public Paciente(String apellido, String nombre, String cuil) {
        super(apellido, nombre, cuil);
    }

    public Paciente(String apellido, String nombre, String cuil,
                    Domicilio domicilio, Afiliado afiliado) {
        super(apellido, nombre, cuil);
        this.domicilio = domicilio;
        this.afiliado = afiliado;
    }
}
