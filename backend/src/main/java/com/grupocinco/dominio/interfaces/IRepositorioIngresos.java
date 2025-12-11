package com.grupocinco.dominio.interfaces;

import com.grupocinco.dominio.EstadoIngreso;
import com.grupocinco.dominio.Ingreso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioIngresos {
    void save(Ingreso ingreso);
    List<Ingreso> findAll();
    Optional<Ingreso> findById(UUID id);
    List<Ingreso> findAllByEstadoIngreso(EstadoIngreso estado);
    List<Ingreso> findAllByEstadoPendienteOrderByNivelEmergenciaAndFechaIngreso();
    List<Ingreso> findAllByEstadoPendienteAndPaciente_Cuil(String cuil);
}
