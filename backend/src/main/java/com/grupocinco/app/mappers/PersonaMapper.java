package com.grupocinco.app.mappers;

import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;

public class PersonaMapper {
    public static PersonaDTO aDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();

        switch (persona.getClass().getSimpleName()) {
            case "Enfermera": dto.setRol(Rol.ENFERMERA.name()); break;
            case "Medico": dto.setRol(Rol.MEDICO.name()); break;
        }

        dto.setApellido(persona.getApellido());
        dto.setNombre(persona.getNombre());
        dto.setCuil(persona.getCuil());

        if (persona instanceof Medico) dto.setMatricula(((Medico)persona).getMatricula());

        return dto;
    }

    public static Persona desdeDTO(PersonaDTO dto) {
        return switch (dto.getRol().toUpperCase()) {
            case "ENFERMERA" -> new Enfermera(dto.getApellido(), dto.getNombre(), dto.getCuil());
            case "MEDICO" -> new Medico(dto.getApellido(), dto.getNombre(), dto.getMatricula(), dto.getCuil());
            default -> throw new IllegalArgumentException("Rol invalido en el dto");
        };
    }
}
