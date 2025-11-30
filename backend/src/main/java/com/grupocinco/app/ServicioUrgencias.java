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
        //String apellido,
        //String nombre,
        Enfermera enfermera,
        String informe,
        String nivelEmergencia,
        String temperatura,
        String frecuenciaCardiaca,
        String frecuenciaRespiratoria,
        String frecuenciaSistolica,
        String frecuenciaDiastolica
    ) throws IllegalArgumentException {

        if (cuil == null || cuil.isBlank()) {
            throw new IllegalArgumentException("Falta el dato CUIL");
        }

        Paciente paciente = dbPacientes.findByCuil(cuil).orElse(null);

        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no existe. Debe registrarlo antes de proceder al ingreso.");
        }

        // Validaciones mandatorias del ingreso
        if (enfermera == null) throw new IllegalArgumentException("Falta el dato Enfermera");

        if (informe == null || informe.isBlank())
            throw new IllegalArgumentException("Falta el dato Informe");

        if (nivelEmergencia == null || nivelEmergencia.isBlank())
            throw new IllegalArgumentException("Falta el dato Nivel de emergencia");

        if (temperatura == null || temperatura.isBlank())
            throw new IllegalArgumentException("Falta el dato Temperatura");

        if (frecuenciaCardiaca == null || frecuenciaCardiaca.isBlank())
            throw new IllegalArgumentException("Falta el dato Frecuencia cardiaca");

        if (frecuenciaRespiratoria == null || frecuenciaRespiratoria.isBlank())
            throw new IllegalArgumentException("Falta el dato Frecuencia respiratoria");

        if (frecuenciaSistolica == null || frecuenciaSistolica.isBlank())
            throw new IllegalArgumentException("Falta el dato Presion sistolica");

        if (frecuenciaDiastolica == null || frecuenciaDiastolica.isBlank())
            throw new IllegalArgumentException("Falta el dato Presion diastolica");

        try {
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
        } catch (IllegalArgumentException e) {
            // Propagamos con mensaje claro al controller
            throw e;
        }
        
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
