package com.grupocinco.domain.valueobject;

import com.grupocinco.app.exceptions.FrecuenciaInvalidaException;

public class FrecuenciaCardiaca extends Frecuencia {
    private FrecuenciaCardiaca(Float frecuencia) {
        super(frecuencia);
    }

    public static FrecuenciaCardiaca of(String frecuencia) {
        float f = validar(frecuencia, "Cardiaca"); 

        if (f < 20 || f > 220) {
            throw new FrecuenciaInvalidaException("La frecuencia cardíaca debe estar entre 20 y 220");
        }

        return new FrecuenciaCardiaca(f);
    }
}
