package com.grupocinco.domain;

import com.grupocinco.app.exceptions.NivelEmergenciaInvalidoException;
import lombok.Getter;

import java.time.Duration;
import java.util.Arrays;

@Getter
public enum NivelEmergencia {
    CRITICA(new Nivel(1, "Critica", Duration.ofMinutes(0))),
    EMERGENCIA(new Nivel(2, "Emergencia", Duration.ofMinutes(30))),
    URGENCIA(new Nivel(3, "Urgencia", Duration.ofMinutes(60))),
    URGENCIA_MENOR(new Nivel(4, "Urgencia menor", Duration.ofMinutes(120))),
    SIN_URGENCIA(new Nivel(5, "Sin Urgencia", Duration.ofMinutes(240))),;

    private final Nivel nivel;

    NivelEmergencia(Nivel nivel) {
        this.nivel = nivel;
    }

    public boolean esValido(String nombre) {
        if (nombre == null) return false;
        return nombre.equals(this.nivel.getNombre());
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
