package com.grupocinco.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {
    private String rol;
    private String nombre;
    private String apellido;
    private String matricula;
}
