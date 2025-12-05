package com.grupocinco.domain;

import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import com.grupocinco.domain.valueobject.FrecuenciaArterial;
import com.grupocinco.domain.valueobject.FrecuenciaCardiaca;
import com.grupocinco.domain.valueobject.FrecuenciaRespiratoria;
import com.grupocinco.domain.valueobject.Temperatura;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ingreso {
    private final UUID id;
    private final LocalDateTime fechaIngreso;
    private final Paciente paciente;
    private final Enfermera enfermera;
    private final String informe;
    private final NivelEmergencia nivelEmergencia;

    private EstadoIngreso estado;

    private final Temperatura temperatura;
    private final FrecuenciaCardiaca frecuenciaCardiaca;
    private final FrecuenciaRespiratoria frecuenciaRespiratoria;
    private final FrecuenciaArterial frecuenciaArterial;

    // médico asignado cuando se reclama el ingreso
    private Medico medico;

    // atencion asignada cuando se registra la atencion
    private Atencion atencion;

    public Ingreso(
            Paciente paciente,
            Enfermera enfermera,
            String informe,
            NivelEmergencia nivelEmergencia,
            Temperatura temperatura,
            FrecuenciaCardiaca frecuenciaCardiaca,
            FrecuenciaRespiratoria frecuenciaRespiratoria,
            FrecuenciaArterial frecuenciaArteial) {
        if (paciente == null)
            throw new IllegalArgumentException("Paciente nulo");
        if (enfermera == null)
            throw new IllegalArgumentException("Enfermera nula");
        if (informe == null || informe.isBlank())
            throw new DatoMandatorioOmitidoException("Falta el dato Informe");

        this.id = UUID.randomUUID();
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

    public void reclamarPor(Medico medico) {
        if (medico == null)
            throw new IllegalArgumentException("Medico nulo");
        if (this.estado != EstadoIngreso.PENDIENTE) {
            throw new IllegalStateException("El ingreso no está en estado PENDIENTE");
        }
        this.medico = medico;
        this.estado = EstadoIngreso.EN_PROCESO;
    }

    public void registrarAtencion(Atencion atencion) {
        if (this.atencion != null) {
            throw new IllegalStateException("El ingreso ya tiene una atencion registrada");
        }
        this.atencion = atencion;
        this.estado = EstadoIngreso.FINALIZADO;
    }

    /*
     * public void setMedico(Medico medico) {
     * this.medico = medico;
     * }
     */
}
