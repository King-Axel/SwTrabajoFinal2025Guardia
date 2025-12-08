package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioIngresos;
import com.grupocinco.domain.Ingreso;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioIngresos implements IRepositorioIngresos {
    private final Map<UUID, Ingreso> dbIngresos = new HashMap<>();

    @Override
    public void save(Ingreso ingreso) {
        dbIngresos.put(ingreso.getId(), ingreso);
    }

    @Override
    public List<Ingreso> findAll() {
        return dbIngresos.values().stream().toList();
    }

    @Override
    public Optional<Ingreso> findById(UUID id) {
        return Optional.ofNullable(dbIngresos.get(id));
    }
}
