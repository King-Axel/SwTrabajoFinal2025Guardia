package com.grupocinco.domain.valueobject;

import lombok.Getter;

@Getter
public class FrecuenciaArterial extends Frecuencia {
    private final Float sistolica;
    private final Float diastolica;

    private FrecuenciaArterial(Float sistolica, Float diastolica) {
        super(null);
        this.sistolica = sistolica;
        this.diastolica = diastolica;
    }

    public static FrecuenciaArterial of(String sistolica, String diastolica) {
        return new FrecuenciaArterial(
            validar(sistolica, "Sistolica"),
            validar(diastolica, "Diastolica")
        );
    }
}
