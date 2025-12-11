package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DomicilioTest {
    @Test
    public void crearDomicilio(){
        String calle = "Rivadavia";
        Integer numero = 1050;
        String localidad = "San Miguel de Tucuman";

        Domicilio domicilio =  new Domicilio(calle, numero, localidad);

        assertThat(calle).isEqualTo(domicilio.getCalle());
        assertThat(numero).isEqualTo(domicilio.getNumero());
        assertThat(localidad).isEqualTo(domicilio.getLocalidad());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearDomicilioConCalleVacioNuloYEspaciado(String calleReq){
        assertThatThrownBy(() -> new Domicilio(calleReq, 123, "Alderetes"))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Calle");
    }

    @ParameterizedTest
    @ValueSource(strings = { "R1v4d4v1a", "Riva'davia" })
    public void crearDomicilioConCalleConCaracteresNumericosYEspeciales(String calleReq){
        Integer numero = 123;
        String localidad = "Alderetes";

        Domicilio domicilio = new Domicilio(calleReq, numero, localidad);

        assertThat(calleReq).isEqualTo(domicilio.getCalle());
        assertThat(numero).isEqualTo(domicilio.getNumero());
        assertThat(localidad).isEqualTo(domicilio.getLocalidad());
    }

    @Test
    public void crearDomicilioConNumeroNulo(){
        Integer numero = null;
        assertThatThrownBy(() -> new Domicilio("Rivadavia", numero, "Alderetes"))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Numero de Calle");
    }

    @Test
    public void crearDomicilioConNumeroNegativo(){
        Integer numero = -1;
        assertThatThrownBy(() -> new Domicilio("Rivadavia", numero, "Alderetes"))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("El Numero de Calle no puede ser negativo");
    }

    @ParameterizedTest
    @ValueSource(strings = { "4lderet3s", "Alde'retes" })
    public void crearDomicilioConLocalidadConCaracteresNumericosYEspeciales(String localidadReq){
        String calle = "Rivadavia";
        Integer numero = 123;

        Domicilio domicilio = new Domicilio(calle, numero, localidadReq);

        assertThat(calle).isEqualTo(domicilio.getCalle());
        assertThat(numero).isEqualTo(domicilio.getNumero());
        assertThat(localidadReq).isEqualTo(domicilio.getLocalidad());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearDomicilioConLocalidadVacioNuloYEspaciado(String localidadReq){
        assertThatThrownBy(() -> new Domicilio("Rivadavia", 123, localidadReq))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Localidad");
    }
}