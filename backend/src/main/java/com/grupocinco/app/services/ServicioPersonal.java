package com.grupocinco.app.services;

import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.exceptions.RolInvalidoException;
import com.grupocinco.app.interfaces.IRepositorioPersonal;
import com.grupocinco.app.mappers.PersonaMapper;
import com.grupocinco.app.repository.RepositorioPersonal;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioPersonal {
    private final IRepositorioPersonal dbPersonal;

    public ServicioPersonal(IRepositorioPersonal dbPersonal) {
        this.dbPersonal = dbPersonal;
    }

    public List<PersonaDTO> listarPersonal() {
        return dbPersonal.findAll().stream().map(PersonaMapper::aDTO).toList();
    }

    public Optional<PersonaDTO> buscarPersonalPorCuil(String cuil) {
        return dbPersonal.findByCuil(cuil).map(PersonaMapper::aDTO);
    }

    public <T extends Persona> T buscarPersonalPorCuilYRol(String cuil, Class<T> tipo) {
        Persona persona = dbPersonal.findByCuil(cuil)
                .orElseThrow(() -> new RuntimeException("No existe personal con el cuil ingresado ("+cuil+")"));

        if (!(tipo.isInstance(persona))) {
            throw new RuntimeException("El cuil ("+cuil+") no corresponde al de algun personal ("+tipo.getSimpleName()+") registrado");
        }

        return tipo.cast(persona);
    }
}
