package com.grupocinco.app.infraestructura.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {
    private String apellido;
    private String nombre;
    private String cuil;
    @NotNull(message = "Falta el dato Domicilio")
    private DomicilioDTO domicilio;
    private AfiliadoDTO afiliado;
}
