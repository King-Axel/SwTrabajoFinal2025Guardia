package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import com.grupocinco.dominio.valueobject.TensionArterial;
import com.grupocinco.dominio.valueobject.FrecuenciaCardiaca;
import com.grupocinco.dominio.valueobject.FrecuenciaRespiratoria;
import com.grupocinco.dominio.valueobject.Temperatura;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class Ingreso {
    private final UUID id;
    private Atencion atencion;
    private final Paciente paciente;
    private final Enfermera enfermera;
    private final NivelEmergencia nivelEmergencia;
    private EstadoIngreso estado;
    private final String informe;
    private final LocalDateTime fechaIngreso;
    private final Temperatura temperatura;
    private final TensionArterial tensionArterial;
    private final FrecuenciaCardiaca frecuenciaCardiaca;
    private final FrecuenciaRespiratoria frecuenciaRespiratoria;

    public Ingreso(
            Paciente paciente,
            Enfermera enfermera,
            String informe,
            NivelEmergencia nivelEmergencia,
            Temperatura temperatura,
            FrecuenciaCardiaca frecuenciaCardiaca,
            FrecuenciaRespiratoria frecuenciaRespiratoria,
            TensionArterial tensionArterial) {
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
        this.tensionArterial = tensionArterial;
    }

    public void registrarAtencion(Atencion atencion) {
        if (this.atencion != null) {
            throw new IllegalStateException("El ingreso ya tiene una atencion registrada");
        }
        this.atencion = atencion;
        this.estado = EstadoIngreso.FINALIZADO;
    }

    public void actualizarEstado() {
        switch (this.estado) {
            case PENDIENTE:
                this.estado = EstadoIngreso.EN_PROCESO;
                break;
            case EN_PROCESO:
                this.estado = EstadoIngreso.FINALIZADO;
                break;
            case FINALIZADO:
                break;
        }
    }
}
