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
    public void guardarPaciente(String cuil, Paciente paciente) {

    }

    @Override
    public Optional<Paciente> obtenerPaciente(String cuil) {
        return Optional.empty();
    }

    @Override
    public Paciente obtenerORegistrarPaciente(String cuil, String apellido, String nombre) {
        return null;
    }

    @Override
    public Paciente registrarPaciente(String cuil, Paciente paciente) {
        return null;
    }
}
