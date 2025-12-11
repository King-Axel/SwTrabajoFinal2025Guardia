package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.InconsistenciaRolPersonalException;
import com.grupocinco.app.infraestructura.util.Rol;
import com.grupocinco.dominio.valueobject.Contrasena;
import com.grupocinco.dominio.valueobject.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CuentaTest {
    @Test
    public void creacionCorrectaDeCuentaCuandoRolCoincideConTipoPersona() {
        Email email = Email.of("correo@gmail.com");
        Contrasena contrasena = Contrasena.of("hashvalido123");
        Rol rol = Rol.MEDICO;
        Medico medico = new Medico("Perez", "José", "998877", "20-30214567-3");

        assertThatCode(() -> new Cuenta(email, contrasena, rol, medico))
                .doesNotThrowAnyException();
    }

    @Test
    public void siRolNoCoincideConTipoPersonaDeberiaObtenerExcepcionInconsistenciaRolPersonalAsociado() {
        Email email = Email.of("correo@gmail.com");
        Contrasena contrasena = Contrasena.of("contrasenahiperseguraYAHASHEADA");
        Rol rol = Rol.MEDICO;
        Enfermera enfermera = new Enfermera("De las nieves", "Maria Antonieta", "27-41234567-6");

        assertThatThrownBy(
                () -> new Cuenta(email, contrasena, rol, enfermera)
        )
                .isExactlyInstanceOf(InconsistenciaRolPersonalException.class)
                .hasMessage("El rol " + rol + " no es compatible con el tipo de personal " + enfermera.getClass().getSimpleName());
    }
}