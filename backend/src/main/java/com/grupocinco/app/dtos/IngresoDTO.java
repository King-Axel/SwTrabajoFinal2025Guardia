package com.grupocinco.app.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngresoDTO {
    @NotNull(message = "Faltan los datos del paciente")
    private PacienteDTO paciente;
    private String informe;
    private String nivelEmergencia;

    private String temperatura;
    private String frecuenciaCardiaca;
    private String frecuenciaRespiratoria;
    private String frecuenciaSistolica;
    private String frecuenciaDiastolica;
}