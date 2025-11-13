package org.example.domain;

public class Paciente {
    private String cuil;
    private String apellido;
    private String nombre;
    private Domicilio domicilio;
    private ObraSocial obraSocial;

    public Paciente(String cuil, String apellido, String nombre) {
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public Paciente(String cuil, String apellido, String nombre,Domicilio domicilio, ObraSocial obraSocial ){
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
        this.obraSocial= obraSocial;
        this.domicilio = domicilio;
    }


    public String getCuil() {
        return this.cuil;
    }
}
