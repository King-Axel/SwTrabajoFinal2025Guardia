package com.grupocinco.app.util;

import java.util.EnumSet;

public enum Rol {
    ENFERMERA(EnumSet.of(
            Permiso.IS202501_REGISTRO_ADMISION,
            Permiso.IS202502_REGISTRO_PACIENTE
    )),
    MEDICO(EnumSet.of(
            Permiso.IS202503_RECLAMO_PACIENTE,
            Permiso.IS202504_REGISTRO_ATENCION
    ));

    private EnumSet<Permiso> permisos;

    Rol(EnumSet<Permiso> permisos){
        this.permisos = permisos;
    }

    public EnumSet<Permiso> getPermisos() {
        return permisos;
    }
}
