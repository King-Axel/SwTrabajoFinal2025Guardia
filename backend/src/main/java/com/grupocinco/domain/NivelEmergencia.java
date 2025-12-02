package com.grupocinco.domain;

import com.grupocinco.app.exceptions.NivelEmergenciaInvalidoException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NivelEmergencia {
    CRITICA("Critica", 1),
    EMERGENCIA("Emergencia", 2),
    URGENCIA("Urgencia", 3),
    URGENCIA_MENOR("Urgencia menor", 4),
    SIN_URGENCIA("Sin Urgencia", 5),;

    private final String nombre;
    private final int prioridad;

    NivelEmergencia(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public boolean esValido(String nombre) {
        if (nombre == null) return false;
        return nombre.equals(this.nombre);
    }

    public static NivelEmergencia desdeString(String nivel) {
        if (nivel == null || nivel.isBlank()) throw new NivelEmergenciaInvalidoException("Falta el dato Nivel de Emergencia");

        return Arrays.stream(NivelEmergencia.values())
                .filter(nE -> nE.esValido(nivel))
                .findFirst()
                .orElseThrow(
                        () -> new NivelEmergenciaInvalidoException("El nivel de emergencia " + nivel + " no existe")
                );
    }
}
