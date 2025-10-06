package org.example.app.interfaces;

import org.example.domain.Paciente;

import java.util.Optional;

public interface RepositorioPacientes {
    public void guardarPaciente(String cuil, Paciente paciente);

    public Optional<Paciente> obtenerPaciente(String cuil);

    public Paciente obtenerORegistrarPaciente(String cuil, String apellido, String nombre);

    public Paciente registrarPaciente(String cuil, Paciente paciente);
}
