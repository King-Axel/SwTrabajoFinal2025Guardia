package com.grupocinco.app.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

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

    // Para validar acceso a funcionalidad segun rol
    public Collection<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Permiso p : permisos) {
            grantedAuthorities.add(new SimpleGrantedAuthority("PERM_" + p.name()));
        }

        return grantedAuthorities;
    }
}
