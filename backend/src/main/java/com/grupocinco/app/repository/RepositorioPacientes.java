package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioPacientes;
import com.grupocinco.domain.Paciente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioPacientes implements IRepositorioPacientes {
    List<Paciente> dbPacientes = new ArrayList<>();

    public RepositorioPacientes() {}

    @Override
    public void save(Paciente paciente) {
        dbPacientes.add(paciente);
    }

    @Override
    public Optional<Paciente> findByCuil(String cuil) {
        return Optional.empty();
    }
}
