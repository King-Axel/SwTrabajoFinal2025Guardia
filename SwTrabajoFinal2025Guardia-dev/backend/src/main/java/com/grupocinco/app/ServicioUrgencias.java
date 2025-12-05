package com.grupocinco.app;

import com.grupocinco.app.interfaces.IRepositorioPacientes;
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
    private final IRepositorioPacientes dbPacientes;
    @Getter
    private final List<Ingreso> listaEspera;
    @Getter
    private final List<Ingreso> ingresosEnAtencion;

    public ServicioUrgencias(IRepositorioPacientes repositorioPacientes) {
        this.dbPacientes = repositorioPacientes;
        this.listaEspera = new ArrayList<>();
        this.ingresosEnAtencion = new ArrayList<>();
    }

    public void registrarIngreso(
            String cuil,
            Enfermera enfermera,
            String informe,
            String nivelEmergencia,
            String temperatura,
            String frecuenciaCardiaca,
            String frecuenciaRespiratoria,
            String frecuenciaSistolica,
            String frecuenciaDiastolica) throws IllegalArgumentException {
        Paciente paciente = dbPacientes.findByCuil(cuil).orElse(null);

        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no existe. Debe registrarlo antes de proceder al ingreso.");
        }

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                NivelEmergencia.desdeString(nivelEmergencia),
                Temperatura.of(temperatura),
                FrecuenciaCardiaca.of(frecuenciaCardiaca),
                FrecuenciaRespiratoria.of(frecuenciaRespiratoria),
                FrecuenciaArterial.of(frecuenciaSistolica, frecuenciaDiastolica));

        this.listaEspera.add(ingreso);
        this.listaEspera.sort(
                Comparator.comparing(Ingreso::getNivelEmergencia)
                        .thenComparing(Ingreso::getFechaIngreso));
    }

    /*
     * public Ingreso reclamarProximoPaciente(Medico medico) {
     * if (listaEspera.isEmpty()) {
     * throw new IllegalStateException("No hay ingresos en lista de espera");
     * }
     * 
     * // sale de la cola (ya viene ordenada por criticidad y hora)
     * Ingreso proximo = listaEspera.remove(0);
     * 
     * // cambia estado y se asigna el médico
     * proximo.setEstado(EstadoIngreso.EN_PROCESO);
     * proximo.setMedico(medico);
     * 
     * // persistimos en la estructura de "en proceso"
     * ingresosEnProceso.put(proximo.getCuilPaciente(), proximo);
     * 
     * return proximo;
     * }
     * 
     * public Optional<Ingreso> obtenerIngresoEnProcesoPorCuil(String cuil) {
     * return Optional.ofNullable(ingresosEnProceso.get(cuil));
     * }
     */

    public Ingreso reclamarProximoIngreso(Medico medico) {
        if (listaEspera.isEmpty()) {
            throw new IllegalStateException("No hay ingresos en lista de espera");
        }

        Ingreso proximo = listaEspera.remove(0);
        proximo.reclamarPor(medico);
        ingresosEnAtencion.add(proximo);
        return proximo;
    }

    public List<Ingreso> obtenerIngresosEnAtencion() {
        return ingresosEnAtencion.stream()
                .sorted(Comparator.comparing(Ingreso::getFechaIngreso))
                .toList();
    }

    public List<Ingreso> obtenerIngresosEnProceso() {
        return ingresosEnAtencion.stream()
                .filter(i -> i.getEstado() == EstadoIngreso.EN_PROCESO)
                .sorted(Comparator.comparing(Ingreso::getFechaIngreso))
                .toList();
    }

    public List<Ingreso> obtenerIngresosEnEspera() {
        return listaEspera
                .stream()
                .sorted(Comparator.comparing(Ingreso::getNivelEmergencia))
                .toList();
    }

    public void registrarAtencion(UUID idIngreso, Medico medico, String informe) {
        Ingreso ingresoAtendido = buscarIngresoAtendido(idIngreso);

        if (ingresoAtendido == null) {
            throw new IllegalArgumentException("El ingreso no existe. Debe registrarlo antes de proceder a la atencion.");
        }

        Atencion atencion = new Atencion(medico, informe);
        ingresoAtendido.registrarAtencion(atencion);
    }

    public Ingreso buscarIngresoAtendido(UUID idIngreso) {
        for (Ingreso ingreso : ingresosEnAtencion) {
            if (ingreso.getId().equals(idIngreso)) {
                return ingreso;
            }
        }
        return null;
    }

}
