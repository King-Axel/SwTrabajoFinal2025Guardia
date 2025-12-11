package com.grupocinco.app.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtencionDTO {
    @NotNull(message = "Faltan los datos de la atencion")
    private IngresoDTO ingreso;
    private String informe;
}
