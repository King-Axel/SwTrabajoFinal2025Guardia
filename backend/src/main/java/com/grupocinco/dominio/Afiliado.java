package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;

@Getter
public class Afiliado {
    private final ObraSocial obraSocial;
    private final String numeroAfiliado;

    public Afiliado(ObraSocial obraSocial, String numeroAfiliado) {
        if (obraSocial == null) throw new DatoMandatorioOmitidoException("Falta el dato ObraSocial");
        if (numeroAfiliado == null || numeroAfiliado.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Numero de Afiliado");

        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }
}