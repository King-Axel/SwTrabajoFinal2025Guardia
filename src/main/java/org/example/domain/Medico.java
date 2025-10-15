package org.example.domain;

public class Medico {
    private final String apellido;
    private final String nombre;

    public Medico(String apellido, String nombre) {
        this.apellido = apellido;
        this.nombre = nombre;
    }
    public String getApellido() { return apellido; }
    public String getNombre() { return nombre; }
}
