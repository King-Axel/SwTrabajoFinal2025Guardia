package org.example.domain;

public class Atencion {
    private String informe;
    private Medico medico;
    private Ingreso ingreso;

    public Atencion(String informe, Medico medico, Ingreso ingreso){
        this.medico = medico;
        this.informe = informe;
        this.ingreso = ingreso;
    }
}
