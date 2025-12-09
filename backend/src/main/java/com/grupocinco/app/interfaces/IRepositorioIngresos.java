package com.grupocinco.app.interfaces;

import com.grupocinco.domain.EstadoIngreso;
import com.grupocinco.domain.Ingreso;
import com.grupocinco.domain.Medico;

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
