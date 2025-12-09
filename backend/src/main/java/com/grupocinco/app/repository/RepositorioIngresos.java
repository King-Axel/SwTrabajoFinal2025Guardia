package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioIngresos;
import com.grupocinco.domain.EstadoIngreso;
import com.grupocinco.domain.Ingreso;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioIngresos implements IRepositorioIngresos {
    private final Map<UUID, Ingreso> dbIngresos = new HashMap<>();

    @Override
    public void save(Ingreso ingreso) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Cambio en ingreso registrado: " + ingreso.toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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

    @Override
    public List<Ingreso> findAllByEstadoIngreso(EstadoIngreso estado) {
        return dbIngresos.values().stream().filter(i -> i.getEstado().equals(estado)).toList();
    }

    @Override
    public List<Ingreso> findAllByEstadoPendienteOrderByNivelEmergenciaAndFechaIngreso() {
        return dbIngresos.values()
                .stream()
                .filter(i -> i.getEstado().equals(EstadoIngreso.PENDIENTE))
                .sorted(Comparator.comparing(Ingreso::getNivelEmergencia)
                        .thenComparing(Ingreso::getFechaIngreso))
                .toList();
    }
}
