package org.example.domain;

public class Medico extends Persona {
    private String matricula;

    public Medico(String apellido, String nombre, String matricula) {
        super(apellido, nombre);
        this.matricula = matricula;
    }

    public String getApellido() { return super.getApellido(); }
    public String getNombre() { return getNombre(); }
}
