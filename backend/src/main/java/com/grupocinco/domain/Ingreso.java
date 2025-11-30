package com.grupocinco.domain;

import com.grupocinco.domain.valueobject.FrecuenciaArterial;
import com.grupocinco.domain.valueobject.FrecuenciaCardiaca;
import com.grupocinco.domain.valueobject.FrecuenciaRespiratoria;
import com.grupocinco.domain.valueobject.Temperatura;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ingreso {
    private final LocalDateTime fechaIngreso;
    private final Paciente paciente;
    private final Enfermera enfermera;
    private final String informe;
    private final NivelEmergencia nivelEmergencia;

    private final EstadoIngreso estado;

    private final Temperatura temperatura;
    private final FrecuenciaCardiaca frecuenciaCardiaca;
    private final FrecuenciaRespiratoria frecuenciaRespiratoria;
    private final FrecuenciaArterial frecuenciaArterial;

    // médico asignado cuando se reclama el ingreso
    // private Medico medico;

    public Ingreso(
            Paciente paciente,
            Enfermera enfermera,
            String informe,
            NivelEmergencia nivelEmergencia,
            Temperatura temperatura,
            FrecuenciaCardiaca frecuenciaCardiaca,
            FrecuenciaRespiratoria frecuenciaRespiratoria,
            FrecuenciaArterial frecuenciaArteial
    ) {
        if (paciente == null) throw new IllegalArgumentException("Paciente nulo");
        if (enfermera == null) throw new IllegalArgumentException("Enfermera nula");
        if (informe == null || informe.isBlank()) throw new IllegalArgumentException("Falta el dato Informe");

        this.fechaIngreso = LocalDateTime.now();
        this.paciente = paciente;
        this.enfermera = enfermera;
        this.informe = informe;
        this.nivelEmergencia = nivelEmergencia;
        this.estado = EstadoIngreso.PENDIENTE;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.frecuenciaArterial = frecuenciaArteial;
    }

    /*public void setMedico(Medico medico) {
        this.medico = medico;
    }*/
}
