package com.grupocinco.domain;

import com.grupocinco.app.exceptions.InconsistenciaRolPersonalException;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CuentaTest {
    @Test
    public void creacionCorrectaDeCuentaCuandoRolCoincideConTipoPersona() {
        Email email = Email.of("correo@gmail.com");
        Contrasena contrasena = Contrasena.of("hashvalido123");
        Rol rol = Rol.MEDICO;
        Medico medico = new Medico("Perez", "José", "998877");

        assertThatCode(() -> new Cuenta(email, contrasena, rol, medico))
                .doesNotThrowAnyException();
    }

    @Test
    public void siRolNoCoincideConTipoPersonaDeberiaObtenerExcepcionInconsistenciaRolPersonalAsociado() {
        Email email = Email.of("correo@gmail.com");
        Contrasena contrasena = Contrasena.of("contrasenahiperseguraYAHASHEADA");
        Rol rol = Rol.MEDICO;
        Enfermera enfermera = new Enfermera("De las nieves", "Maria Antonieta");

        assertThatThrownBy(
                () -> new Cuenta(email, contrasena, rol, enfermera)
        )
                .isExactlyInstanceOf(InconsistenciaRolPersonalException.class)
                .hasMessage("El rol " + rol + " no es compatible con el tipo de personal " + enfermera.getClass().getSimpleName());
    }
}