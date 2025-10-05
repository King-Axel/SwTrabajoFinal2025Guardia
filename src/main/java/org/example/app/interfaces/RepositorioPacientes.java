package org.example.app.interfaces;

import org.example.domain.Paciente;

import java.util.Optional;

public interface RepositorioPacientes {
    public void guardarPaciente(Paciente paciente);

    public Optional<Paciente> buscarPacientePorCuil(String cuil);
}
