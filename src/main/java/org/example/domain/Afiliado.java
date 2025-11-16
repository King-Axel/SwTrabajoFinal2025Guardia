package org.example.domain;

import org.example.app.exceptions.DomainException;

import java.util.Objects;

public class Afiliado {
    private ObraSocial obraSocial;
    private String numeroAfiliado;

    public Afiliado(ObraSocial obraSocial, String numeroAfiliado) {
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

}


