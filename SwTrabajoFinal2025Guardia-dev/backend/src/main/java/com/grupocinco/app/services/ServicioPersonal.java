package com.grupocinco.app.services;

import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.mappers.PersonaMapper;
import com.grupocinco.app.repository.RepositorioPersonal;
import com.grupocinco.domain.Persona;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPersonal {
    private final RepositorioPersonal dbPersonal;

    public ServicioPersonal() {
        dbPersonal = new RepositorioPersonal();
    }

    public List<PersonaDTO> listarPersonal() {
        List<Persona> personal = dbPersonal.findAll();
        return personal.stream().map(PersonaMapper::aDTO).toList();
    }
}
