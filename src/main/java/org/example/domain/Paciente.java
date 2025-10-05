package org.example.domain;

public class Paciente {
    private String cuil;
    private String apellido;
    private String nombre;
    private String obraSocial;

    public Paciente(String cuil, String apellido, String nombre, String obraSocial) {
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
        this.obraSocial = obraSocial;
    }

    public String getCuil() {
        return cuil;
    }
}
