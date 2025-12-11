package com.grupocinco.dominio.interfaces;

import com.grupocinco.dominio.Persona;

import java.util.List;
import java.util.Optional;

public interface IRepositorioPersonal {
    List<Persona> findAll();
    Optional<Persona> findByCuil(String cuil);
    void save(Persona persona);
}
