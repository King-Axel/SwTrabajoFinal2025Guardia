package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.RepositorioPacientes;
import com.grupocinco.domain.Paciente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioDePacientes implements RepositorioPacientes {
    List<Paciente> dbPacientes = new ArrayList<>();

    public RepositorioDePacientes() {}

    @Override
    public void guardarPaciente(Paciente paciente) {
        dbPacientes.add(paciente);
    }

    @Override
    public Optional<Paciente> obtenerPaciente(String cuil) {
        return Optional.empty();
    }

    @Override
    public void registrarPaciente(Paciente paciente) {
    }
}
