package com.grupocinco.app.infraestructura.repository;

import com.grupocinco.dominio.interfaces.IRepositorioIngresos;
import com.grupocinco.dominio.EstadoIngreso;
import com.grupocinco.dominio.Ingreso;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioIngresos implements IRepositorioIngresos {
    private final List<Ingreso> dbIngresos = new ArrayList<>();

    @Override
    public void save(Ingreso ingreso) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Cambio en ingreso registrado: " + ingreso.toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        for(int i = 0; i < dbIngresos.size(); i++) {
            if (dbIngresos.get(i).getId().equals(ingreso.getId())) {
                dbIngresos.set(i, ingreso);
                return;
            }
        }

        dbIngresos.add(ingreso);
    }

    @Override
    public List<Ingreso> findAll() {
        return dbIngresos;
    }

    @Override
    public Optional<Ingreso> findById(UUID id) {
        return dbIngresos.stream().filter(ingreso -> ingreso.getId().equals(id)).findFirst();
    }

    @Override
    public List<Ingreso> findAllByEstadoIngreso(EstadoIngreso estado) {
        return dbIngresos.stream().filter(i -> i.getEstado().equals(estado)).toList();
    }

    @Override
    public List<Ingreso> findAllByEstadoPendienteOrderByNivelEmergenciaAndFechaIngreso() {
        return dbIngresos.stream()
                .filter(i -> i.getEstado().equals(EstadoIngreso.PENDIENTE))
                .sorted(Comparator.comparing(Ingreso::getNivelEmergencia)
                        .thenComparing(Ingreso::getFechaIngreso))
                .toList();
    }

    @Override
    public List<Ingreso> findAllByEstadoPendienteAndPaciente_Cuil(String cuil) {
        return dbIngresos.stream()
                .filter(
                        i -> i.getPaciente().getCuil().equals(cuil) && i.getEstado().equals(EstadoIngreso.PENDIENTE)
                ).toList();
    }
}
