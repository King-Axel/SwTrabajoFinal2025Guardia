package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Paciente;

import java.util.Optional;

public interface RepositorioPacientes {
    void save(Paciente paciente);
    Optional<Paciente> findByCuil(String cuil);
}
