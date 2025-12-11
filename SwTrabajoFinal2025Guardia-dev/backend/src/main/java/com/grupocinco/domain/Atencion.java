package com.grupocinco.domain;

import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;

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
