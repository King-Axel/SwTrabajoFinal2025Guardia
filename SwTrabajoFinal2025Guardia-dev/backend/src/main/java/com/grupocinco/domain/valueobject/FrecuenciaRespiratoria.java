package com.grupocinco.domain.valueobject;

import com.grupocinco.app.exceptions.FrecuenciaInvalidaException;

public class FrecuenciaRespiratoria extends Frecuencia {
    private FrecuenciaRespiratoria(Float frecuencia) {
        super(frecuencia);
    }

    public static FrecuenciaRespiratoria of(String frecuencia) {
        float f = validar(frecuencia, "Respiratoria");

        if (f < 0 || f > 60) {
            throw new FrecuenciaInvalidaException("La frecuencia respiratoria debe estar entre 0 y 60");
        }

        return new FrecuenciaRespiratoria(f);
    }
}
