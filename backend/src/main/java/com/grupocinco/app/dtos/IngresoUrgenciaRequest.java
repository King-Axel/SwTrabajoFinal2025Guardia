package com.grupocinco.app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngresoUrgenciaRequest {
    private String cuil;
    private String apellido;
    private String nombre;
    private String informe;
    private String nivelEmergencia;

    private Double temperatura;
    private Double frecuenciaCardiaca;
    private Double frecuenciaRespiratoria;
    private Double frecuenciaSistolica;
    private Double frecuenciaDiastolica;
}