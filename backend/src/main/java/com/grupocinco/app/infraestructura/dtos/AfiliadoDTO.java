package com.grupocinco.app.infraestructura.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AfiliadoDTO {
    private ObraSocialDTO obraSocial;
    private String numeroAfiliado;
}
