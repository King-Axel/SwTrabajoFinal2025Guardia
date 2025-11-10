package org.example.domain;

public class Paciente {
    private String cuil;
    private String apellido;
    private String nombre;

    public Paciente(String cuil, String apellido, String nombre) {
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public String getCuil() {
        return this.cuil;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getNombre() {
        return this.nombre;
    }
}
