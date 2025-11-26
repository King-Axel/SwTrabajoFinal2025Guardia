package com.grupocinco.app.dtos;

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
    private DomicilioDTO domicilio;
    private AfiliadoDTO afiliado;
}
