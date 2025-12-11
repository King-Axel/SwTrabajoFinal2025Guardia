package com.grupocinco.dominio.exceptions;

import com.grupocinco.app.infraestructura.util.Rol;
import com.grupocinco.dominio.Persona;

public class InconsistenciaRolPersonalException extends RuntimeException {
    public InconsistenciaRolPersonalException(Rol rol, Persona persona) {
        super("El rol " + rol + " no es compatible con el tipo de personal " + persona.getClass().getSimpleName());
    }
}
