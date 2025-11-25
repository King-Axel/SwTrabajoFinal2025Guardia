package com.grupocinco.domain.valueobject;

public class FrecuenciaRespiratoria extends Frecuencia {
    private FrecuenciaRespiratoria(Float frecuencia) {
        super(frecuencia);
    }

    public static FrecuenciaRespiratoria of(String frecuencia) {
        return new FrecuenciaRespiratoria(validar(frecuencia, "Respiratoria"));
    }
}
