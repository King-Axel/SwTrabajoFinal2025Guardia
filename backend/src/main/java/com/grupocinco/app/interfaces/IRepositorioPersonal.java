package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Persona;

import java.util.List;
import java.util.Optional;

public interface IRepositorioPersonal {
    List<Persona> findAll();
    Optional<Persona> findByCuil(String cuil);
}
