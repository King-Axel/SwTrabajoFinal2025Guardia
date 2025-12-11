package com.grupocinco.app.infraestructura.mappers;

import com.grupocinco.app.infraestructura.dtos.PacienteDTO;
import com.grupocinco.dominio.Paciente;

public class PacienteMapper {
    public static Paciente desdeDTO(PacienteDTO dto) {
        return new Paciente(
                dto.getApellido(), dto.getNombre(), dto.getCuil(),
                DomicilioMapper.desdeDTO(dto.getDomicilio()),
                // La afiliacion puede ser null
                dto.getAfiliado() != null
                        ? AfiliadoMapper.desdeDTO(dto.getAfiliado())
                        : null
        );
    }

    public static PacienteDTO aDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();

        dto.setApellido(paciente.getApellido());
        dto.setNombre(paciente.getNombre());
        dto.setCuil(paciente.getCuil());
        dto.setDomicilio(DomicilioMapper.aDTO(paciente.getDomicilio()));

        if(paciente.getAfiliado() != null) dto.setAfiliado(AfiliadoMapper.aDTO(paciente.getAfiliado()));

        return dto;
    }
}
