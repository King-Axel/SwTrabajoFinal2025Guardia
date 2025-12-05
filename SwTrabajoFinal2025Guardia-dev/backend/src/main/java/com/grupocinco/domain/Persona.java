package com.grupocinco.domain;

import com.grupocinco.app.exceptions.CuilInvalidoException;
import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;

@Getter
public abstract class Persona {
    private final String apellido;
    private final String nombre;
    private final String cuil;

    protected Persona(String apellido, String nombre, String cuil) {
        if (apellido == null || apellido.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Apellido");
        if (nombre == null || nombre.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Nombre");
        if (cuil == null || cuil.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Cuil");
        validarFormatoCuil(cuil);

        this.apellido = apellido;
        this.nombre = nombre;
        this.cuil = cuil;
    }

    private void validarFormatoCuil(String cuil) {
        String regex = "\\d{2}-\\d{8}-\\d";
        if (!cuil.matches(regex)) {
            throw new CuilInvalidoException("Cuil invalido (formato esperado: XX-XXXXXXXX-X)");
        }
    }
}
