package com.grupocinco.dominio.interfaces;

import com.grupocinco.dominio.Paciente;

import java.util.List;
import java.util.Optional;

public interface IRepositorioPacientes {
    void save(Paciente paciente);
    Optional<Paciente> findByCuil(String cuil);
    List<Paciente> findAll();
}
