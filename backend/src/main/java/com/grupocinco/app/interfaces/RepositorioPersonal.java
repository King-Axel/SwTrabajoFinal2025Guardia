package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Persona;

import java.util.List;

public interface RepositorioPersonal {
    List<Persona> findAll();
}
