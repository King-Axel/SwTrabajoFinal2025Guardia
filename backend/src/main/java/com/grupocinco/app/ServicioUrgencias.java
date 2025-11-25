package com.grupocinco.app;

import com.grupocinco.app.interfaces.RepositorioPacientes;
import com.grupocinco.domain.*;
import com.grupocinco.domain.*;
import com.grupocinco.domain.valueobject.FrecuenciaArterial;
import com.grupocinco.domain.valueobject.FrecuenciaCardiaca;
import com.grupocinco.domain.valueobject.FrecuenciaRespiratoria;
import com.grupocinco.domain.valueobject.Temperatura;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioUrgencias {
    private final RepositorioPacientes dbPacientes;
    @Getter
    private final List<Ingreso> listaEspera;

    // private final Map<String, Ingreso> ingresosEnProceso = new HashMap<>();

    public ServicioUrgencias(RepositorioPacientes repositorioPacientes) {
        this.dbPacientes = repositorioPacientes;
        this.listaEspera = new ArrayList<>();
    }

    public void registrarIngreso(
        String cuil,
        String apellido,
        String nombre,
        Enfermera enfermera,
        String informe,
        String nivelEmergencia,
        String temperatura,
        String frecuenciaCardiaca,
        String frecuenciaRespiratoria,
        String frecuenciaSistolica,
        String frecuenciaDiastolica
    ) throws IllegalArgumentException {
        Paciente paciente = dbPacientes.obtenerPaciente(cuil)
                .orElse(null);

        if (paciente == null) {
            paciente = new Paciente(apellido, nombre, cuil);
            dbPacientes.registrarPaciente(paciente);
        }

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                NivelEmergencia.desdeString(nivelEmergencia),
                Temperatura.of(temperatura),
                FrecuenciaCardiaca.of(frecuenciaCardiaca),
                FrecuenciaRespiratoria.of(frecuenciaRespiratoria),
                FrecuenciaArterial.of(frecuenciaSistolica, frecuenciaDiastolica)
        );

        this.listaEspera.add(ingreso);
        this.listaEspera.sort(
                Comparator.comparing(Ingreso::getNivelEmergencia)
                        .thenComparing(Ingreso::getFechaIngreso)
        );
    }

    /*public Ingreso reclamarProximoPaciente(Medico medico) {
        if (listaEspera.isEmpty()) {
            throw new IllegalStateException("No hay ingresos en lista de espera");
        }

        // sale de la cola (ya viene ordenada por criticidad y hora)
        Ingreso proximo = listaEspera.remove(0);

        // cambia estado y se asigna el médico
        proximo.setEstado(EstadoIngreso.EN_PROCESO);
        proximo.setMedico(medico);

        // persistimos en la estructura de "en proceso"
        ingresosEnProceso.put(proximo.getCuilPaciente(), proximo);

        return proximo;
    }

    public Optional<Ingreso> obtenerIngresoEnProcesoPorCuil(String cuil) {
        return Optional.ofNullable(ingresosEnProceso.get(cuil));
    }*/

    public List<Ingreso> obtenerIngresosEnEspera() {
        return listaEspera
        .stream()
        .sorted(Comparator.comparing(Ingreso::getNivelEmergencia).reversed())
        .toList();
    }
}
