package com.grupocinco.app.infraestructura.dtos;

import com.grupocinco.dominio.EstadoIngreso;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class IngresoDTO {
    @NotNull(message = "Faltan los datos del paciente")
    private PacienteDTO paciente;
    private UUID id;
    private String informe;
    private String nivelEmergencia;
    private EstadoIngreso estado;
    private String temperatura;
    private String frecuenciaCardiaca;
    private String frecuenciaRespiratoria;
    private String frecuenciaSistolica;
    private String frecuenciaDiastolica;
    private LocalDateTime fechaIngreso;
}