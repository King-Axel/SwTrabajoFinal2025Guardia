package org.example.domain;

import java.time.LocalDateTime;

public class Ingreso {
    private final LocalDateTime fechaIngreso;
    private final Paciente paciente;
    private final Enfermera enfermera;
    private final String informe;
    private final NivelEmergencia nivelEmergencia;

    private EstadoIngreso estado;

    private final Float temperatura;
    private final Float frecuenciaCardiaca;
    private final Float frecuenciaRespiratoria;
    private final Float frecuenciaSistolica;
    private final Float frecuenciaDiastolica;

    // médico asignado cuando se reclama el ingreso
    private Medico medico;

    public Ingreso(
            Paciente paciente,
            Enfermera enfermera,
            String informe,
            NivelEmergencia nivelEmergencia,
            Float temperatura,
            Float frecuenciaCardiaca,
            Float frecuenciaRespiratoria,
            Float frecuenciaSistolica,
            Float frecuenciaDiastolica
    ) {
        this.fechaIngreso = LocalDateTime.now();
        this.paciente = paciente;
        this.enfermera = enfermera;
        this.informe = informe;
        this.nivelEmergencia = nivelEmergencia;
        this.estado = EstadoIngreso.PENDIENTE; // antes era String "Pendiente"
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.frecuenciaSistolica = frecuenciaSistolica;
        this.frecuenciaDiastolica = frecuenciaDiastolica;
    }

    public String getCuilPaciente() { return this.paciente.getCuil(); }
    public int getPrioridadNivelEmergencia() { return this.nivelEmergencia.getPrioridad(); }
    public LocalDateTime getFechaIngreso() { return this.fechaIngreso; }

    public EstadoIngreso getEstado() {
        return estado;
    }
    public void setEstado(EstadoIngreso estado) {
        this.estado = estado;
    }

    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
