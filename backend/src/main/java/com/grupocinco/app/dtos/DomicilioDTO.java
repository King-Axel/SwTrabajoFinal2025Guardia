package com.grupocinco.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioDTO {
    private String calle;
    private Integer numeroCalle;
    private String localidad;
}
