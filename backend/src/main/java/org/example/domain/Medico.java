package org.example.domain;

public class Medico {
    private String matricula;
    private String apellido;
    private String nombre;

    public Medico(String matricula) {
        this.matricula = matricula;
    }

    public Medico(String apellido, String nombre) {
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public String getApellido() { return apellido; }
    public String getNombre() { return nombre; }
}
