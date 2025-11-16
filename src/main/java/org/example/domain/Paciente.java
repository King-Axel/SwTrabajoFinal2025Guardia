package org.example.domain;

import org.example.app.exceptions.DomainException;

import java.util.Objects;

public class Paciente extends Persona{
    private Domicilio domicilio;
    private Afiliado afiliado;

    public Paciente(String cuil, String apellido, String nombre) {
        super(cuil, apellido, nombre);
    }

    public Paciente(String cuil, String apellido, String nombre, Domicilio domicilio, Afiliado afiliado) {
        super(cuil, apellido, nombre);
        this.afiliado = afiliado;
        this.domicilio = domicilio;
    }

    public Domicilio getDomicilio() { return this.domicilio; }

    public Afiliado getAfiliado() { return this.afiliado; }

}
