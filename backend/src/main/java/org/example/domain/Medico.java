package org.example.domain;

public class Medico {
    private String matricula;
    private final String apellido;
    private final String nombre;

    public Medico(String apellido, String nombre, String matricula) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.matricula = matricula;
    }
    public String getApellido() { return apellido; }
    public String getNombre() { return nombre; }
}
