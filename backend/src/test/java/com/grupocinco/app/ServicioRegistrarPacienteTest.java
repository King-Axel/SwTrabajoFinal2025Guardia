package com.grupocinco.app;

import com.grupocinco.app.dtos.AfiliadoDTO;
import com.grupocinco.app.dtos.DomicilioDTO;
import com.grupocinco.app.dtos.ObraSocialDTO;
import com.grupocinco.app.dtos.PacienteDTO;
import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import com.grupocinco.app.exceptions.ObraSocialInexistenteException;
import com.grupocinco.app.exceptions.PacienteNoAfiliadoException;
import com.grupocinco.app.interfaces.IRepositorioObrasSociales;
import com.grupocinco.app.interfaces.IRepositorioPacientes;
import com.grupocinco.app.mappers.AfiliadoMapper;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;
import com.grupocinco.domain.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

class ServicioRegistrarPacienteTest {
    private ServicioRegistrarPaciente servicioRegistrarPaciente;
    private IRepositorioPacientes repositorioPacientes;
    private IRepositorioObrasSociales repositorioObrasSociales;

    @BeforeEach
    void setUp() {
        this.repositorioPacientes = mock(IRepositorioPacientes.class);
        this.repositorioObrasSociales = mock(IRepositorioObrasSociales.class);

        this.servicioRegistrarPaciente = new ServicioRegistrarPaciente(this.repositorioPacientes, this.repositorioObrasSociales);
    }

    private PacienteDTO crearDTO() {
        var dto = new PacienteDTO();

        dto.setCuil("20-12345678-1");
        dto.setNombre("Benjamin");
        dto.setApellido("Gonzalez");
        dto.setDomicilio(new DomicilioDTO(
                "Urquiza", 123, "Alderetes"
        ));
        dto.setAfiliado(null); //sin obra social por defecto

        return dto;
    }

    @Test
    public void registroCorrectoConObraSocialExistenteYAfiliacionValida() {
        PacienteDTO dto = crearDTO();

        ObraSocial obraSocial = new ObraSocial("PAMI");
        Afiliado afiliado = new Afiliado(obraSocial,"PAMI-002233");

        dto.setAfiliado(AfiliadoMapper.aDTO(afiliado));

        when(repositorioObrasSociales.findByName(obraSocial.getNombre()))
                .thenReturn(Optional.of(obraSocial));

        when(repositorioObrasSociales.isAfiliated(any(Afiliado.class))).thenReturn(true);

        doNothing().when(repositorioPacientes).save(any(Paciente.class));

        Paciente paciente = servicioRegistrarPaciente.registrarPaciente(dto);

        assertThat(paciente).isNotNull();
        assertThat(dto.getCuil()).isEqualTo(paciente.getCuil());
        assertThat(dto.getNombre()).isEqualTo(paciente.getNombre());
        assertThat(dto.getApellido()).isEqualTo(paciente.getApellido());

        assertThat(paciente.getDomicilio()).isNotNull();
        assertThat(dto.getDomicilio().getCalle()).isEqualTo(paciente.getDomicilio().getCalle());
        assertThat(dto.getDomicilio().getNumeroCalle()).isEqualTo(paciente.getDomicilio().getNumero());
        assertThat(dto.getDomicilio().getLocalidad()).isEqualTo(paciente.getDomicilio().getLocalidad());

        assertThat(paciente.getAfiliado()).isNotNull();
        assertThat(dto.getAfiliado().getObraSocial().getNombre())
                .isEqualTo(paciente.getAfiliado().getObraSocial().getNombre());
        assertThat(dto.getAfiliado().getNumeroAfiliado())
                .isEqualTo(paciente.getAfiliado().getNumeroAfiliado());

        verify(repositorioObrasSociales, times(1))
                .findByName(obraSocial.getNombre());
        verify(repositorioObrasSociales, times(1))
                .isAfiliated(any(Afiliado.class));

        verify(repositorioPacientes, times(1))
                .save(any(Paciente.class));
    }

    @Test
    public void registroCorrectoSinObraSocial() {
        PacienteDTO dto = crearDTO();
        dto.setAfiliado(null);

        doNothing().when(repositorioPacientes).save(any(Paciente.class));

        Paciente paciente = servicioRegistrarPaciente.registrarPaciente(dto);

        assertThat(paciente).isNotNull();
        assertThat(dto.getCuil()).isEqualTo(paciente.getCuil());
        assertThat(dto.getNombre()).isEqualTo(paciente.getNombre());
        assertThat(dto.getApellido()).isEqualTo(paciente.getApellido());

        assertThat(paciente.getDomicilio()).isNotNull();
        assertThat(dto.getDomicilio().getCalle()).isEqualTo(paciente.getDomicilio().getCalle());
        assertThat(dto.getDomicilio().getNumeroCalle()).isEqualTo(paciente.getDomicilio().getNumero());
        assertThat(dto.getDomicilio().getLocalidad()).isEqualTo(paciente.getDomicilio().getLocalidad());

        assertThat(paciente.getAfiliado()).isNull();

        verifyNoInteractions(repositorioObrasSociales);
        verify(repositorioPacientes, times(1))
                .save(any(Paciente.class));
    }

    @Test
    public void registroConObraSocialInexistenteDeberiaLanzarExcepcion() {
        PacienteDTO dto = crearDTO();

        ObraSocialDTO obraSocial = new ObraSocialDTO(String.valueOf(UUID.randomUUID()), "hola");
        dto.setAfiliado(new AfiliadoDTO(obraSocial, "123456789"));

        when(repositorioObrasSociales.findByName(obraSocial.getNombre())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicioRegistrarPaciente.registrarPaciente(dto))
                .isExactlyInstanceOf(ObraSocialInexistenteException.class)
                .hasMessage("Obra social inexistente");

        verify(repositorioObrasSociales, times(1))
                .findByName(obraSocial.getNombre());
        verify(repositorioObrasSociales, never())
                .isAfiliated(any());
        verify(repositorioPacientes, times(1))
                .findByCuil(dto.getCuil());
    }

    @Test
    public void registroConObraSocialExistentePeroNoAfiliadoDeberiaLanzarExcepcion() {
        PacienteDTO dto = crearDTO();

        ObraSocial obraSocial = new ObraSocial("PAMI");
        Afiliado afiliado = new Afiliado(obraSocial, "123456780");

        dto.setAfiliado(AfiliadoMapper.aDTO(afiliado));

        when(repositorioObrasSociales.findByName(obraSocial.getNombre())).thenReturn(Optional.of(obraSocial));
        when(repositorioObrasSociales.isAfiliated(any(Afiliado.class))).thenReturn(false);

        assertThatThrownBy(() -> servicioRegistrarPaciente.registrarPaciente(dto))
                .isExactlyInstanceOf(PacienteNoAfiliadoException.class)
                .hasMessage("El paciente no esta afiliado a la obra social");

        verify(repositorioObrasSociales, times(1))
                .findByName(obraSocial.getNombre());
        verify(repositorioObrasSociales, times(1))
                .isAfiliated(any(Afiliado.class));
        verify(repositorioPacientes, times(1))
                .findByCuil(dto.getCuil());
    }

    @Test
    public void registroConPacienteNullDeberiaLanzarExcepcion() {
        PacienteDTO dto = null;

        assertThatThrownBy(() -> servicioRegistrarPaciente.registrarPaciente(dto))
                .isExactlyInstanceOf(DatoMandatorioOmitidoException.class)
                .hasMessage("Los datos del paciente son obligatorios");

        verifyNoInteractions(repositorioObrasSociales);
    }
}