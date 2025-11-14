package com.grupocinco.domain.valueobject;

import com.grupocinco.app.security.PasswordHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class ContrasenaTest {
    @Test
    public void cuandoLaContrasenaEsValidaEstaDeberiaSerHasheadaCorrectamente() {
        // Preparacion
        String contrasena = "zapallitorelleno34";

        // Ejecucion
        Contrasena contrasenaObj = Contrasena.fromRaw(contrasena);

        // Validacion
        assertThat(contrasenaObj).isNotNull()
                .isInstanceOf(Contrasena.class);
        assertThat(contrasenaObj.get()).isNotEqualTo(contrasena);
        assertThat(PasswordHasher.matches(contrasena, contrasenaObj.get())).isTrue();

        assertThat(PasswordHasher.hash(contrasena)).isNotEqualTo(contrasena);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "        ", " " })
    public void cuandoContrasenaEstaAusenteSeDeberiaLanzarExcepcionIndicandoObligatoriedad(String contrasenaRec) {
        // Preparacion
        String contrasena = contrasenaRec;

        // Ejecucion y Validacion
        assertThatThrownBy(() -> Contrasena.fromRaw(contrasena))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contrasena es obligatoria");
    }

    @Test
    public void cuandoLaContrasenaEsCortaDeberiaLanzarseUnaExcepcionIndicandoLaLongitudMinima() {
        // Preparacion
        String contrasena = "contra";

        // Ejecucion y Validacion
        assertThatThrownBy(() -> Contrasena.fromRaw(contrasena))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contrasena debe tener al menos 8 caracteres");
    }

    @Test
    public void reconstruccionCorrectaDeContrasenaDesdeHash() {
        // Preparacion
        Contrasena contrasenaHash = Contrasena.fromRaw("PanConJamon");

        // Ejecucion
        Contrasena contrasena = Contrasena.fromHashed(contrasenaHash.get());

        // Validacion
        assertThat(contrasenaHash.get()).isEqualTo(contrasena.get());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "        ", " " })
    public void tratarDeReconstruirUnaContrasenaAusenteDeberiaLanzarUnaExcepcionIndicandoObligatoriedad(String contrasenaRec) {
        // Preparacion
        String contrasena = contrasenaRec;

        // Ejecucion y Validacion
        assertThatThrownBy(() -> Contrasena.fromHashed(contrasena))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contrasena ingresada no es valida");
    }
}