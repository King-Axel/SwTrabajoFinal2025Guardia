package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.CuilInvalidoException;
import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PacienteTest {
    @Test
    public void crearPaciente(){
        String apellido = "Lopez";
        String nombre = "Hector";
        String cuil = "20-12345678-1";

        Paciente paciente = new Paciente(apellido, nombre, cuil);

        assertThat(apellido).isEqualTo(paciente.getApellido());
        assertThat(nombre).isEqualTo(paciente.getNombre());
        assertThat(cuil).isEqualTo(paciente.getCuil());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearPacienteConApellidoVacioNuloYEspaciado(String apellidoReq){
        assertThatThrownBy(() -> new Paciente(apellidoReq, "Hector", "20-12345678-1"))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Apellido");
    }

    @ParameterizedTest
    @ValueSource(strings = { "L0p3z", "@López" })
    public void crearPacienteConApellidoConCaracteresNumericosYEspeciales(String apellidoReq){
        String nombre = "Hector";
        String cuil = "20-12345678-1";

        Paciente paciente = new Paciente(apellidoReq, nombre, cuil);

        assertThat(apellidoReq).isEqualTo(paciente.getApellido());
        assertThat(nombre).isEqualTo(paciente.getNombre());
        assertThat(cuil).isEqualTo(paciente.getCuil());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearPacienteConNombreNuloVacioYEspaciado(String nombreReq){
        assertThatThrownBy(() -> new Paciente("Lopez", nombreReq, "20-12345678-1"))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Nombre");
    }

    @ParameterizedTest
    @ValueSource(strings = { "H3ctor", "H'ector" })
    public void crearPacienteConNombreConCaracteresNumericosYEspeciales(String nombreReq){
        String apellido = "Lopez";
        String cuil = "20-12345678-1";

        Paciente paciente = new Paciente(apellido, nombreReq, cuil);

        assertThat(apellido).isEqualTo(paciente.getApellido());
        assertThat(nombreReq).isEqualTo(paciente.getNombre());
        assertThat(cuil).isEqualTo(paciente.getCuil());
    }

    @Test
    public void crearPacienteConCuilConFormatoInvalido(){
        String cuil = "20123456781";
        String apellido = "Lopez";
        String nombre = "Hector";

        assertThatThrownBy(() -> new Paciente(apellido, nombre, cuil))
                .isExactlyInstanceOf(CuilInvalidoException.class)
                .hasMessage("Cuil invalido (formato esperado: XX-XXXXXXXX-X)");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "    " })
    public void crearPacienteConCuilVacioNuloYEspaciado(String cuilReq){
        assertThatThrownBy(() -> new Paciente("Lopez", "Hector", cuilReq))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Falta el dato Cuil");
    }
}