package com.grupocinco.domain.valueobject;

public class FrecuenciaCardiaca extends Frecuencia {
    private FrecuenciaCardiaca(Float frecuencia) {
        super(frecuencia);
    }

    public static FrecuenciaCardiaca of(String frecuencia) {
        return new FrecuenciaCardiaca(validar(frecuencia, "Cardiaca"));
    }
}
