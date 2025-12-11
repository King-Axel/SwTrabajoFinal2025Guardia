package com.grupocinco.app.mappers;

import com.grupocinco.app.dtos.ObraSocialDTO;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;

public class ObraSocialMapper {
    public static ObraSocial desdeDTO(ObraSocialDTO dto) {
        return new ObraSocial(dto.getNombre());
    }

    public static ObraSocialDTO aDTO(ObraSocial obraSocial) {
        ObraSocialDTO dto = new ObraSocialDTO();

        dto.setId(obraSocial.getId());
        dto.setNombre(obraSocial.getNombre());

        return dto;
    }
}
