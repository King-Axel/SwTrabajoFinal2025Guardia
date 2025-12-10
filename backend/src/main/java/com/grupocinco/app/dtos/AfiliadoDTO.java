package com.grupocinco.app.dtos;

import com.grupocinco.domain.ObraSocial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AfiliadoDTO {
    private ObraSocialDTO obraSocial;
    private String numeroAfiliado;
}
