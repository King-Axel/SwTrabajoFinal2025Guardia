package com.grupocinco.domain.valueobject;

import com.grupocinco.app.exceptions.FrecuenciaInvalidaException;

import lombok.Getter;

@Getter
public class TensionArterial extends Frecuencia {
    private final Float sistolica;
    private final Float diastolica;

    private TensionArterial(Float sistolica, Float diastolica) {
        super(null);
        this.sistolica = sistolica;
        this.diastolica = diastolica;
    }

    public static TensionArterial of(String sistolica, String diastolica) {
        float sis = validar(sistolica, "Sistolica");
        float dia = validar(diastolica, "Diastolica");

        if (sis < 40 || sis > 250) {
            throw new FrecuenciaInvalidaException("La presión sistólica debe estar entre 40 y 250");
        }

        if (dia < 20 || dia > 150) {
            throw new FrecuenciaInvalidaException("La presión diastólica debe estar entre 20 y 150");
        }

        return new TensionArterial(sis, dia);
    }
}
