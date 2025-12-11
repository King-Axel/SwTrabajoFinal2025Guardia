package com.grupocinco.app.infraestructura.mappers;

import com.grupocinco.app.infraestructura.dtos.DomicilioDTO;
import com.grupocinco.dominio.Domicilio;

public class DomicilioMapper {
    public static Domicilio desdeDTO(DomicilioDTO dto) {
        Domicilio domicilio = new Domicilio(
                dto.getCalle(), dto.getNumeroCalle(), dto.getLocalidad()
        );

        return domicilio;
    }

    public static DomicilioDTO aDTO(Domicilio domicilio) {
        DomicilioDTO dto = new DomicilioDTO();

        dto.setCalle(domicilio.getCalle());
        dto.setNumeroCalle(domicilio.getNumero());
        dto.setLocalidad(domicilio.getLocalidad());

        return dto;
    }
}
