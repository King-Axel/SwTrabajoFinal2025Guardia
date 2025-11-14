package com.grupocinco.domain.valueobject;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class EmailTest {
    @ParameterizedTest
    @ValueSource(strings = {"test12@test.com", "email123.@gmail.com", "cuenta@hotmail.com", "123123@a123.com", "juan.perez@example.com",
            "usuario_2025@dominio.org", "pepito-test@sub.dominio.com", "user+tag@service.net", "contact%info@mail-server.co", "UPPERCASE@DOMAIN.COM",
            "MiXed.CaSe@Email.ExAmPlE.Ar", "nombre.apellido123@empresa-tech.com", "a.b.c.d@multi.segment.domain.com", "cliente-01@web-store.io",
            "yo+promo2025@newsletter.club", "robot_42-test@dev-zone.dev", "email.valido@servidor.education", "super.user%account@host.uk"})
    public void cuandoEmailCumplePatronDeCorreoSeConsideraValido(String emailRec) {
        // Preparacion
        String email = emailRec;

        // Ejecucion
        Email emailObj = Email.of(email);

        //Validacion
        assertThat(emailObj).isNotNull()
        .isInstanceOf(Email.class)
        .hasFieldOrPropertyWithValue("email", email);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"        "})
    public void cuandoEmailEstaAusenteSeDeberiaLanzarExcepcionIndicandoObligatoriedad(String emailRec) {
        // Preparacion
        String email = emailRec;

        // Ejecucion y Validacion
        assertThatThrownBy(() -> Email.of(email))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("El email es obligatorio");
    }

    @ParameterizedTest
    @ValueSource(strings = { "sinarroba.test.com", "connumeros@alfinal.12", "correo@sinpartefinal", "ñandu42@gmail.com", "unaletraalfinal@test.a",
            "finalconguion@bajo.c_m", "caracteresnopermitidos<>asd@asd.asd", "PANCONJAMON@23<>.com", "con espacio@gmail.com"
    })
    public void cuandoEmailNoCumplePatronDeCorreoSeConsideraInvalidoYSeLanzaExcepcion(String emailRec) {
        // Preparacion
        String email = emailRec;

        // Ejecucion y Validacion
        assertThatThrownBy(() -> Email.of(email))
        .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("El email ingresado no es valido");
    }
}