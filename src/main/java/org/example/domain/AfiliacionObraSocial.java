package org.example.domain;

public class AfiliacionObraSocial {
    private ObraSocial obraSocial;
    private String numeroAfiliado;

    public AfiliacionObraSocial(ObraSocial obraSocial, String numeroAfiliado) {
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


