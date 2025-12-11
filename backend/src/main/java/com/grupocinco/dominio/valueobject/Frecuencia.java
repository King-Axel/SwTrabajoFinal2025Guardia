package com.grupocinco.dominio.valueobject;

import com.grupocinco.dominio.exceptions.FrecuenciaInvalidaException;

public abstract class Frecuencia {
    protected final Float frecuencia;

    protected Frecuencia(Float frecuencia) {
        this.frecuencia = frecuencia;
    }

    protected static float validar(String valor, String tipo) {
        float valorParseado;

        if (valor == null) throw new FrecuenciaInvalidaException(
            "Falta el dato Frecuencia " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase()
        );

        try {
            valorParseado = Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La frecuencia debe ser un numero");
        }

        if (valorParseado < 0) throw new FrecuenciaInvalidaException("La frecuencia no puede ser negativa");

        return valorParseado;
    }

    public Float getFrecuencia() {
        return this.frecuencia;
    }
}