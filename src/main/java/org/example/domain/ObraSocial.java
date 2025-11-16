package org.example.domain;

import org.example.app.exceptions.CuilInvalidoException;
import org.example.app.exceptions.DomainException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class ObraSocial {
    private UUID id;
    private String nombre;

    public ObraSocial(UUID id, String nombre){
        assertNombreNotEmpty(nombre);
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    private void assertNombreNotEmpty(String nombre) {
        if (Objects.isNull(nombre) || nombre.isBlank()) {
            throw new DomainException("El nombre de la obra social no puede ser nulo ni estar en blanco");
        }
    }

}
