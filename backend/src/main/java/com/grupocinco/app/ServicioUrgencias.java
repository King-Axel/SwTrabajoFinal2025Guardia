package com.grupocinco.app;

import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.app.exceptions.PacienteInexistenteException;
import com.grupocinco.app.interfaces.IRepositorioIngresos;
import com.grupocinco.app.interfaces.IRepositorioPacientes;
import com.grupocinco.app.mappers.IngresoMapper;
import com.grupocinco.domain.*;
import com.grupocinco.domain.valueobject.TensionArterial;
import com.grupocinco.domain.valueobject.FrecuenciaCardiaca;
import com.grupocinco.domain.valueobject.FrecuenciaRespiratoria;
import com.grupocinco.domain.valueobject.Temperatura;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioUrgencias {
    private final IRepositorioPacientes dbPacientes;
    private final IRepositorioIngresos dbIngresos;
    @Getter
    private List<Ingreso> listaEspera;

    public ServicioUrgencias(IRepositorioPacientes repositorioPacientes, IRepositorioIngresos repositorioIngresos) {
        this.dbPacientes = repositorioPacientes;
        this.dbIngresos = repositorioIngresos;
        this.listaEspera = new ArrayList<>();
    }

    public void actualizarColaEspera() {
        this.listaEspera = dbIngresos.findAllByEstadoPendienteOrderByNivelEmergenciaAndFechaIngreso();

        for (Ingreso ingreso : this.listaEspera) {
            System.out.println(ingreso.getPaciente().getCuil());
        }
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
            String frecuenciaDiastolica
    ) throws IllegalArgumentException {
        Paciente paciente = dbPacientes.findByCuil(cuil).orElseThrow(
                () -> new PacienteInexistenteException(
                        "El paciente no existe. Debe registrarlo antes de proceder al ingreso."
                )
        );

        if (!dbIngresos.findAllByEstadoPendienteAndPaciente_Cuil(cuil).isEmpty())
            throw new RuntimeException("Un paciente no puede tener mas de un ingreso pendiente a la vez");

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                NivelEmergencia.desdeString(nivelEmergencia),
                Temperatura.of(temperatura),
                FrecuenciaCardiaca.of(frecuenciaCardiaca),
                FrecuenciaRespiratoria.of(frecuenciaRespiratoria),
                TensionArterial.of(frecuenciaSistolica, frecuenciaDiastolica));

        dbIngresos.save(ingreso);
        actualizarColaEspera();
    }

    public Ingreso reclamarProximoIngreso() {
        if (listaEspera.isEmpty()) {
            throw new IllegalStateException("No hay ingresos en lista de espera");
        }

        Ingreso proximo = listaEspera.get(0);
        proximo.actualizarEstado();
        dbIngresos.save(proximo);
        actualizarColaEspera();
        return proximo;
    }

    public List<IngresoDTO> obtenerIngresosEnAtencion() {
        return dbIngresos.findAllByEstadoIngreso(EstadoIngreso.EN_PROCESO)
                .stream().map(IngresoMapper::aDTO).toList();
    }

    public List<IngresoDTO> obtenerIngresosEnEspera() {
        return listaEspera.stream().map(IngresoMapper::aDTO).toList();
    }

    public void registrarAtencion(UUID idIngreso, Medico medico, String informe) {
        Ingreso ingresoAtendido = buscarIngresoAtendido(idIngreso);

        Atencion atencion = new Atencion(medico, informe);
        ingresoAtendido.registrarAtencion(atencion);
        dbIngresos.save(ingresoAtendido);
    }

    public Ingreso buscarIngresoAtendido(UUID idIngreso) {
        return dbIngresos.findById(idIngreso).orElseThrow(
                () -> new IllegalArgumentException("El ingreso no existe. Debe registrarlo antes de proceder a la atencion.")
        );
    }

    public List<IngresoDTO> obtenerTodosNoPendientes() {
        List<Ingreso> ingresos = new ArrayList<>();

        ingresos.addAll(dbIngresos.findAllByEstadoIngreso(EstadoIngreso.EN_PROCESO));
        ingresos.addAll(dbIngresos.findAllByEstadoIngreso(EstadoIngreso.FINALIZADO));

        return ingresos.stream().map(IngresoMapper::aDTO).toList();
    }
}
