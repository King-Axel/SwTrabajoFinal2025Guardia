package org.example.domain;

import java.util.UUID;

public class ObraSocial {
    private UUID id;
    private String nombre;

    public ObraSocial(UUID id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
