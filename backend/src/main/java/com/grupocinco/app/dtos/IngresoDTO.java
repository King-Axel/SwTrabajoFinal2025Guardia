package com.grupocinco.app.dtos;

import com.grupocinco.domain.Paciente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngresoDTO {
    private PacienteDTO paciente;
    private String informe;
    private String nivelEmergencia;

    private Float temperatura;
    private Float frecuenciaCardiaca;
    private Float frecuenciaRespiratoria;
    private Float frecuenciaSistolica;
    private Float frecuenciaDiastolica;
}