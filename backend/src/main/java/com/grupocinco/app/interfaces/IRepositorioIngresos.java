package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Ingreso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioIngresos {
    void save(Ingreso ingreso);
    List<Ingreso> findAll();
    Optional<Ingreso> findById(UUID id);
}
