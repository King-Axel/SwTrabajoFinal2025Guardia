package com.grupocinco.app.infraestructura.mappers;

import com.grupocinco.app.infraestructura.dtos.ObraSocialDTO;
import com.grupocinco.dominio.ObraSocial;

public class ObraSocialMapper {
    public static ObraSocial desdeDTO(ObraSocialDTO dto) {
        return new ObraSocial(dto.getNombre());
    }

    public static ObraSocialDTO aDTO(ObraSocial obraSocial) {
        ObraSocialDTO dto = new ObraSocialDTO();

        dto.setId(String.valueOf(obraSocial.getId()));
        dto.setNombre(obraSocial.getNombre());

        return dto;
    }
}
