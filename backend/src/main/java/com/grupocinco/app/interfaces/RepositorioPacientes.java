package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Paciente;

import java.util.Optional;

public interface RepositorioPacientes {
    void guardarPaciente(String cuil, Paciente paciente);

    Optional<Paciente> obtenerPaciente(String cuil);

    Paciente obtenerORegistrarPaciente(String cuil, String apellido, String nombre);

    Paciente registrarPaciente(String cuil, Paciente paciente);
}
