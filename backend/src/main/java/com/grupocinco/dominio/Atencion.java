package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;

public class Atencion {
    private final Medico medico;
    private final String informe;

    public Atencion(Medico medico, String informe) {
        if (medico == null)
            throw new IllegalArgumentException("Medico nulo");
        if (informe == null || informe.isBlank())
            throw new DatoMandatorioOmitidoException("Falta el dato Informe");
        this.medico = medico;
        this.informe = informe;
    }
}
