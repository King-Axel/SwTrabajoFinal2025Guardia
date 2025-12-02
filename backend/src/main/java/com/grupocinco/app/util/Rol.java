package com.grupocinco.app.util;

import com.grupocinco.app.exceptions.RolInvalidoException;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Getter
public enum Rol {
    ENFERMERA(EnumSet.of(
            Permiso.IS202501_REGISTRO_ADMISION,
            Permiso.IS202502_REGISTRO_PACIENTE
    )),
    MEDICO(EnumSet.of(
            Permiso.IS202503_RECLAMO_PACIENTE,
            Permiso.IS202504_REGISTRO_ATENCION
    ));

    private final EnumSet<Permiso> permisos;

    Rol(EnumSet<Permiso> permisos){
        this.permisos = permisos;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Permiso p : permisos) {
            grantedAuthorities.add(new SimpleGrantedAuthority("PERM_" + p.name()));
        }

        return grantedAuthorities;
    }

    public static Rol desdeString(String rol) {
        if (rol == null || rol.isBlank()) throw new RolInvalidoException("Se debe ingresar un rol para crear una cuenta");

        return Arrays.stream(Rol.values())
                .filter(r -> r.esValido(rol))
                .findFirst()
                .orElseThrow(
                        () -> new RolInvalidoException("El rol " + rol + " no existe")
                );
    }

    private boolean esValido(String nombre) {
        return nombre.equalsIgnoreCase(this.name());
    }
}
