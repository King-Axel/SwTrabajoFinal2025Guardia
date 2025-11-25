package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Paciente;

import java.util.Optional;

public interface RepositorioPacientes {
    void guardarPaciente(Paciente paciente);

    Optional<Paciente> obtenerPaciente(String cuil);

    void registrarPaciente(Paciente paciente);
}
