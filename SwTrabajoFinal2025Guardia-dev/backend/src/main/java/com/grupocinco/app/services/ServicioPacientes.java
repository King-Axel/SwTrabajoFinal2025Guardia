package com.grupocinco.app.services;

import com.grupocinco.app.dtos.PacienteDTO;
import com.grupocinco.app.mappers.PacienteMapper;
import com.grupocinco.app.repository.RepositorioPacientes;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioPacientes {
    private final RepositorioPacientes repositorio;

    public ServicioPacientes(RepositorioPacientes repositorio) {
        this.repositorio = repositorio;
    }

    public Optional<PacienteDTO> buscarPorCuil(String cuil) {
        return repositorio.findByCuil(cuil).map(PacienteMapper::aDTO);
    }
}
