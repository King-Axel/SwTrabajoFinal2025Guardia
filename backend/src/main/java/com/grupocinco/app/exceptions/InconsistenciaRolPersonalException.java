package com.grupocinco.app.exceptions;

import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Persona;

public class InconsistenciaRolPersonalException extends RuntimeException {
    public InconsistenciaRolPersonalException(Rol rol, Persona persona) {
        super("El rol " + rol + " no es compatible con el tipo de personal " + persona.getClass().getSimpleName());
    }
}
