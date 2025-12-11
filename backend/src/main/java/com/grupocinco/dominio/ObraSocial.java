package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ObraSocial {
    private final UUID id;
    private final String nombre;

    public ObraSocial(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Nombre de Obra Social");

        this.id = UUID.randomUUID();
        this.nombre = nombre;
    }
}
