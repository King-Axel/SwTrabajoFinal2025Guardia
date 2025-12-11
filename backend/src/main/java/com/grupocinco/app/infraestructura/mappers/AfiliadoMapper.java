package com.grupocinco.app.infraestructura.mappers;

import com.grupocinco.app.infraestructura.dtos.AfiliadoDTO;
import com.grupocinco.dominio.Afiliado;

public class AfiliadoMapper {
    public static Afiliado desdeDTO(AfiliadoDTO dto) {
        return new Afiliado(
                ObraSocialMapper.desdeDTO(dto.getObraSocial()),
                dto.getNumeroAfiliado()
        );
    }

    public static AfiliadoDTO aDTO(Afiliado afiliado) {
        AfiliadoDTO dto = new AfiliadoDTO();

        dto.setNumeroAfiliado(afiliado.getNumeroAfiliado());
        dto.setObraSocial(ObraSocialMapper.aDTO(afiliado.getObraSocial()));

        return dto;
    }
}
