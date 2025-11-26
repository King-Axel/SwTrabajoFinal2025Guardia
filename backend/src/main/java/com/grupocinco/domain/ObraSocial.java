package com.grupocinco.domain;

import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ObraSocial {
    @Setter
    private Long id;
    private final String nombre;

    public ObraSocial(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Nombre de Obra Social");

        this.nombre = nombre;
    }
}
