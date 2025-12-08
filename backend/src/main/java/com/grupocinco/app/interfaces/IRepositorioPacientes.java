package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Paciente;

import java.util.List;
import java.util.Optional;

public interface IRepositorioPacientes {
    void save(Paciente paciente);
    Optional<Paciente> findByCuil(String cuil);
    List<Paciente> findAll();
}
