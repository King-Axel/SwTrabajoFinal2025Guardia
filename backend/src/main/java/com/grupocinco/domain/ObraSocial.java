package com.grupocinco.domain;

import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class ObraSocial {
    @Setter
    private UUID id;
    private final String nombre;

    public ObraSocial(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Nombre de Obra Social");

        this.id = UUID.randomUUID();
        this.nombre = nombre;
    }
}
