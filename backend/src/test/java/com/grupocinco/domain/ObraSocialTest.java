package com.grupocinco.domain;

import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ObraSocialTest {
    @Test
    public void crearObraSocial(){
        Long id = 1L;
        String nombre = "Boreal";

        ObraSocial obraSocial = new ObraSocial(nombre);
        obraSocial.setId(id);

        assertEquals(id, obraSocial.getId());
        assertEquals(nombre, obraSocial.getNombre());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearObraSocialConNombreVacioNuloYEspaciado(String nombreReq){
        assertThatThrownBy(() -> new ObraSocial(nombreReq))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Nombre de Obra Social");
    }

    @ParameterizedTest
    @ValueSource(strings = { "B0r3al", "B'oreal" })
    public void crearObraSocialConNombreConCaracteresNumericosYCaracteresEspeciales(String nombreReq) {
        Long id = 1L;
        ObraSocial obraSocial = new ObraSocial(nombreReq);
        obraSocial.setId(id);

        assertEquals(id, obraSocial.getId());
        assertEquals(nombreReq, obraSocial.getNombre());
    }
}