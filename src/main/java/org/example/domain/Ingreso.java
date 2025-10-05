package org.example.domain;

import java.time.LocalDateTime;

public class Ingreso {
    private Paciente paciente;
    private Enfermera enfermera;
    private LocalDateTime fechaIngreso;
    private String informe;
    private NivelEmergencia nivelEmergencia;
    private EstadoIngreso estadoIngreso;
    private Float temperatura;
    private Float frecuenciaCardiaca;
    private Float frecuenciaRespiratoria;
    private Float frecuenciaSistolica;
    private Float frecuenciaDiastolica;

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
        this.paciente = paciente;
        this.enfermera = enfermera;
        this.informe = informe;
        this.nivelEmergencia = nivelEmergencia;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.frecuenciaSistolica = frecuenciaSistolica;
        this.frecuenciaDiastolica = frecuenciaDiastolica;

        this.fechaIngreso = LocalDateTime.now();
        this.estadoIngreso = EstadoIngreso.PENDIENTE;
    }

    public String getCuilPaciente() {
        return this.paciente.getCuil();
    }
}
