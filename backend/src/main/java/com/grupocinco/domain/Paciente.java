package com.grupocinco.domain;

public class Paciente extends Persona {
    private String cuil;

    public Paciente(String cuil, String apellido, String nombre) {
        super(apellido, nombre);
        this.cuil = cuil;
    }

    public String getCuil() {
        return this.cuil;
    }

    public String getApellido() {
        return super.getApellido();
    }

    public String getNombre() {
        return this.getNombre();
    }
}
