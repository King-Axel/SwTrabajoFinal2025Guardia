package org.example.domain;

import java.time.LocalDateTime;

public class Ingreso {
    private final LocalDateTime fechaIngreso;
    private final Paciente paciente;
    private final Enfermera enfermera;
    private final String informe;
    private final NivelEmergencia nivelEmergencia;
    private final String estado;
    private final Float temperatura;
    private final Float frecuenciaCardiaca;
    private final Float frecuenciaRespiratoria;
    private final Float frecuenciaSistolica;
    private final Float frecuenciaDiastolica;

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
        fechaIngreso = LocalDateTime.now();
        this.paciente = paciente;
        this.enfermera = enfermera;
        this.informe = informe;
        this.nivelEmergencia = nivelEmergencia;
        estado = "Pendiente";
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.frecuenciaSistolica = frecuenciaSistolica;
        this.frecuenciaDiastolica = frecuenciaDiastolica;
    }

    public String getCuilPaciente() {
        return this.paciente.getCuil();
    }

    public int getPrioridadNivelEmergencia() {
        return this.nivelEmergencia.getPrioridad();
    }

    public LocalDateTime getFechaIngreso() {
        return this.fechaIngreso;
    }
}
