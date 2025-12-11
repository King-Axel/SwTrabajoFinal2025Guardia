package com.grupocinco.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class ContrasenaTest {
    private final PasswordEncoder encoder =
            new Argon2PasswordEncoder(16, 32, 1, 4096, 3);

    @Test
    public void cuandoLaContrasenaEsValidaDebePasarValidacionesDelValueObject() {
        String contrasena = "zapallitorelleno34";

        assertThatCode(
                () -> Contrasena.validarRaw(contrasena)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "        ", " " })
    public void cuandoContrasenaEstaAusenteSeDeberiaLanzarExcepcionIndicandoObligatoriedad(String contrasenaReq) {
        assertThatThrownBy(() -> Contrasena.validarRaw(contrasenaReq))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña es obligatoria");
    }

    @Test
    public void cuandoLaContrasenaEsCortaDeberiaLanzarseUnaExcepcionIndicandoLaLongitudMinima() {
        String contrasena = "contra";

        assertThatThrownBy(() -> Contrasena.validarRaw(contrasena))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña debe tener al menos 8 caracteres");
    }

    @Test
    public void reconstruccionCorrectaDeContrasenaDesdeHash() {
        String contrasenaHash = encoder.encode("PanConJamon");

        Contrasena contrasena = Contrasena.of(contrasenaHash);

        assertThat(contrasena).isNotNull();
        assertThat(contrasena.get()).isEqualTo(contrasenaHash);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "        ", " " })
    public void tratarDeReconstruirUnaContrasenaDesdeHashAusenteDeberiaLanzarUnaExcepcion(String contrasenaHashReq) {
        assertThatThrownBy(() -> Contrasena.of(contrasenaHashReq))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("El hash no puede estar vacío");
    }
}