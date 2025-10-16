package org.example.app;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.*;

import java.util.*;

public class ServicioUrgencias {
    private RepositorioPacientes dbPacientes;
    private List<Ingreso> listaEspera;

    // NUEVO: almacenamiento simple de ingresos "en proceso"
    private final Map<String, Ingreso> ingresosEnProceso = new HashMap<>();


    public ServicioUrgencias(RepositorioPacientes repositorioPacientes) {
        this.dbPacientes =  repositorioPacientes;
        this.listaEspera =  new ArrayList<>();
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
        String mensajeExcepcionDatoFaltante = "Error: falta el dato ";

        NivelEmergencia nivel =
                (nivelEmergencia != null)
                        ? Arrays.stream(NivelEmergencia.values())
                            .filter(
                                    nivelEm -> nivelEm.esValido(nivelEmergencia)
                            )
                            .findFirst()
                            .orElseThrow (
                                    () -> new RuntimeException("Nivel de emergencia invalido")
                            )
                        : null;

        Paciente paciente =
                dbPacientes.obtenerORegistrarPaciente(cuil, apellido, nombre);

        Map<String, Object> campos = new HashMap<>();
        campos.put("Cuil", cuil);
        campos.put("Apellido", apellido);
        campos.put("Nombre", nombre);
        campos.put("Informe", informe);
        campos.put("Nivel de Emergencia", nivelEmergencia);
        campos.put("Temperatura", temperatura);
        campos.put("Frecuencia Cardiaca", frecuenciaCardiaca);
        campos.put("Frecuencia Respiratoria", frecuenciaRespiratoria);
        campos.put("Frecuencia Sistolica", frecuenciaSistolica);
        campos.put("Frecuencia Diastolica", frecuenciaDiastolica);

        campos.forEach((nombreCampo, valor) -> {
            if (
                    valor == null || valor instanceof String s && s.isBlank()
            ) throw new  IllegalArgumentException(mensajeExcepcionDatoFaltante + nombreCampo);
        });

        Float temp = Float.parseFloat(temperatura);
        Float frecCard = Float.parseFloat(frecuenciaCardiaca);
        Float frecResp = Float.parseFloat(frecuenciaRespiratoria);
        Float frecSist = Float.parseFloat(frecuenciaSistolica);
        Float frecDiast = Float.parseFloat(frecuenciaDiastolica);

        if(frecCard < 0 || frecResp < 0) throw new  IllegalArgumentException(
                "Error: La frecuencia cardiaca y la frecuencia respiratoria no pueden ser valores negativos"
        );

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                nivel,
                temp,
                frecCard,
                frecResp,
                frecSist,
                frecDiast
        );

        this.listaEspera.add(ingreso);
        this.listaEspera.sort(
                Comparator.comparing(Ingreso::getPrioridadNivelEmergencia)
                        .thenComparing(Ingreso::getFechaIngreso)
        );
    }

    public List<Ingreso> getListaEspera() {
        return this.listaEspera;
    }

    // =========================
    // NUEVO: Reclamar paciente
    // =========================
    public Ingreso reclamarProximoPaciente(Medico medico) {
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
    }
}
