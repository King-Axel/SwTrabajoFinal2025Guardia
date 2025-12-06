package com.grupocinco.app.util;

import com.grupocinco.app.exceptions.RolInvalidoException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.grupocinco.app.util.Permiso.*;
import static org.assertj.core.api.Assertions.*;

class RolTest {
    @ParameterizedTest
    @ValueSource(strings = { "Medico", "MEDICO", "medico", "MeDiCo", "Enfermera", "ENFERMERA", "enfermera", "EnFeRmErA" })
    public void rolValido(String rolReq) {
        Rol rol = Rol.desdeString(rolReq);

        assertThat(rol).isNotNull();
        assertThat(Rol.values()).contains(rol);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Medica", "Enfermero", "Doctor", "123123", "Rol del hospital 2" })
    public void rolInvalido(String rolReq) {
        assertThatThrownBy(() -> Rol.desdeString(rolReq))
                .isExactlyInstanceOf(RolInvalidoException.class)
                .hasMessage("El rol " + rolReq + " no existe");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "   " })
    public void rolInvalidoNull(String rolReq) {
        assertThatThrownBy(() -> Rol.desdeString(rolReq))
                .isExactlyInstanceOf(RolInvalidoException.class)
                .hasMessage("Se debe ingresar un rol para crear una cuenta");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "IS202501_REGISTRO_ADMISION",
            "IS202502_REGISTRO_PACIENTE",
            "IS202505_VER_COLA_ESPERA"
    })
    public void rolEnfermeraTienePermisosRegistroAdmisionRegistroPaciente(String permisoReq) {
        Rol rol = Rol.ENFERMERA;

        assertThat(rol.getPermisos())
                .contains(valueOf(permisoReq));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "IS202503_RECLAMO_PACIENTE",
            "IS202504_REGISTRO_ATENCION"
    })
    public void rolEnfermeraNoTienePermisosReclamoPacienteRegistroAtencion(String permisoReq) {
        Rol rol = Rol.ENFERMERA;

        assertThat(rol.getPermisos())
                .doesNotContain(valueOf(permisoReq));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "IS202503_RECLAMO_PACIENTE",
            "IS202504_REGISTRO_ATENCION",
            "IS202505_VER_COLA_ESPERA"
    })
    public void rolMedicoTienePermisosReclamoPacienteRegistroAtencion(String permisoReq) {
        Rol rol = Rol.MEDICO;

        assertThat(rol.getPermisos())
                .contains(valueOf(permisoReq));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "IS202501_REGISTRO_ADMISION",
            "IS202502_REGISTRO_PACIENTE"
    })
    public void rolMedicoNoTienePermisosRegistroAdmisionRegistroPaciente(String permisoReq) {
        Rol rol = Rol.MEDICO;

        assertThat(rol.getPermisos())
                .doesNotContain(valueOf(permisoReq));
    }
}