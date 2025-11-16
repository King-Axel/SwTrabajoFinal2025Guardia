package org.example.app.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.example.app.dtos.RegistroPacienteDTO;
import org.example.app.exceptions.DatoMandatorioOmitidoException;
import org.example.app.exceptions.ObraSocialInexistenteException;
import org.example.app.exceptions.PacienteNoAfiliadoException;
import org.example.app.interfaces.RepositorioPacientes;
import org.example.app.interfaces.ServicioObraSocial;
import org.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ServicioRegistrarPacienteTest {
    private ServicioRegistrarPaciente servicioRegistrarPaciente;
    private RepositorioPacientes repositorioPacientes;
    private ServicioObraSocial servicioObraSocial;

    @BeforeEach
    void setUp() {
        this.repositorioPacientes = mock(RepositorioPacientes.class);
        this.servicioObraSocial = mock(ServicioObraSocial.class);

        this.servicioRegistrarPaciente = new ServicioRegistrarPaciente(this.repositorioPacientes, this.servicioObraSocial);
    }

    private RegistroPacienteDTO crearDTO() {
        RegistroPacienteDTO dto = new RegistroPacienteDTO();
        dto.setCuil("20-12345678-1");
        dto.setNombre("Benjamin");
        dto.setApellido("Gonzalez");
        dto.setCalle("Urquiza");
        dto.setNumero(123);
        dto.setLocalidad("Alderetes");
        dto.setNombreObraSocial(null); //sin obra social por defecot
        dto.setNumeroAfiliado(null);
        return dto;
    }

    @Test
    public void registroCorrectoConObraSocialExistenteYAfiliacionValida() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        UUID idObraSocial = UUID.randomUUID();
        dto.setNombreObraSocial("Boreal");
        dto.setNumeroAfiliado("123456789");

        ObraSocial obraSocial = new ObraSocial(idObraSocial,"Boreal");

        when(servicioObraSocial.obtenerObraSocialPorNombre(dto.getNombreObraSocial())).thenReturn(obraSocial);

        Afiliado afiliado = new Afiliado(obraSocial,"123456789");

        when(servicioObraSocial.estaAfiliado(any(Afiliado.class))).thenReturn(true);
        when(repositorioPacientes.registrarPaciente(eq(dto.getCuil()), any(Paciente.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        //Ejecucion
        Paciente paciente = servicioRegistrarPaciente.registrarPaciente(dto);

        //Validacion
        assertNotNull(paciente);
        assertEquals(dto.getCuil(),paciente.getCuil());
        assertEquals(dto.getNombre(),paciente.getNombre());
        assertEquals(dto.getApellido(),paciente.getApellido());
        assertNotNull(paciente.getDomicilio());
        assertEquals(dto.getCalle(),paciente.getDomicilio().getCalle());
        assertEquals(dto.getNumero(),paciente.getDomicilio().getNumero());
        assertEquals(dto.getLocalidad(),paciente.getDomicilio().getLocalidad());
        assertNotNull(paciente.getAfiliado());
        assertEquals(obraSocial,paciente.getAfiliado().getObraSocial());
        assertEquals(dto.getNumeroAfiliado(),paciente.getAfiliado().getNumeroAfiliado());

        verify(servicioObraSocial, times(1))
                .obtenerObraSocialPorNombre(dto.getNombreObraSocial());
        verify(servicioObraSocial, times(1))
                .estaAfiliado(any(Afiliado.class));
        verify(repositorioPacientes, times(1))
                .registrarPaciente(eq(dto.getCuil()), any(Paciente.class));
    }

    @Test
    public void registroCorrectoSinObraSocial() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setNombreObraSocial(null);
        dto.setNumeroAfiliado(null);

        when(repositorioPacientes.registrarPaciente(eq(dto.getCuil()), any(Paciente.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        //Ejecucion
        Paciente paciente = servicioRegistrarPaciente.registrarPaciente(dto);

        //Validacion
        assertNotNull(paciente);
        assertEquals(dto.getCuil(),paciente.getCuil());
        assertEquals(dto.getNombre(),paciente.getNombre());
        assertEquals(dto.getApellido(),paciente.getApellido());
        assertNotNull(paciente.getDomicilio());
        assertEquals(dto.getCalle(),paciente.getDomicilio().getCalle());
        assertEquals(dto.getNumero(),paciente.getDomicilio().getNumero());
        assertEquals(dto.getLocalidad(),paciente.getDomicilio().getLocalidad());
        assertNull(paciente.getAfiliado());

        verifyNoInteractions(servicioObraSocial);
        verify(repositorioPacientes, times(1))
                .registrarPaciente(eq(dto.getCuil()), any(Paciente.class));
    }

    @Test
    public void registroConObraSocialInexistenteDeberiaLanzarExcepcion() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        UUID idObraSocial = UUID.randomUUID();
        dto.setNombreObraSocial("hola");
        dto.setNumeroAfiliado("123456789");

        when(servicioObraSocial.obtenerObraSocialPorNombre(dto.getNombreObraSocial())).thenReturn(null);

        //Ejecucion y Validacion
        ObraSocialInexistenteException thrown = assertThrows(ObraSocialInexistenteException.class, () -> {
                                                            servicioRegistrarPaciente.registrarPaciente(dto);
                                                            });
        assertEquals("Obra social inexistente", thrown.getMessage());

        verify(servicioObraSocial, times(1))
                .obtenerObraSocialPorNombre(dto.getNombreObraSocial());
        verify(servicioObraSocial, never())
                .estaAfiliado(any());
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConObraSocialExistentePeroNoAfiliadoDeberiaLanzarExcepcion() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        UUID idObraSocial = UUID.randomUUID();
        dto.setNombreObraSocial("PAMI");
        dto.setNumeroAfiliado("123456780");

        ObraSocial obraSocial = new ObraSocial(idObraSocial, "PAMI");
        Afiliado afiliado = new Afiliado(obraSocial,"123456780");

        when(servicioObraSocial.obtenerObraSocialPorNombre("PAMI")).thenReturn(obraSocial);
        when(servicioObraSocial.estaAfiliado(afiliado)).thenReturn(false);

        //Ejecucion y Validacion
        PacienteNoAfiliadoException thrown = assertThrows(PacienteNoAfiliadoException.class, () -> {
                                                        servicioRegistrarPaciente.registrarPaciente(dto);
                                                        });
        assertEquals("El paciente no esta afiliado a la obra social", thrown.getMessage());

        verify(servicioObraSocial, times(1))
                .obtenerObraSocialPorNombre(dto.getNombreObraSocial());
        verify(servicioObraSocial, times(1))
                .estaAfiliado(any(Afiliado.class));
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConCuilOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setCuil(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
                                                            servicioRegistrarPaciente.registrarPaciente(dto);
                                                            });
        assertEquals("El cuil es obligatorio", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConApellidoOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setApellido(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
            servicioRegistrarPaciente.registrarPaciente(dto);
        });
        assertEquals("El apellido es obligatorio", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConNombreOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setNombre(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
            servicioRegistrarPaciente.registrarPaciente(dto);
        });
        assertEquals("El nombre es obligatorio", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConCalleOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setCalle(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
            servicioRegistrarPaciente.registrarPaciente(dto);
        });
        assertEquals("La calle es obligatoria", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConNumeroOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setNumero(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
            servicioRegistrarPaciente.registrarPaciente(dto);
        });
        assertEquals("El numero de domicilio es obligatorio", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

    @Test
    public void registroConLocalidadOmitidoDeberiaLanzarExcepcionDeDatoObligatorio() {
        //Preparacion
        RegistroPacienteDTO dto = crearDTO();
        dto.setLocalidad(null);

        // Ejecucion y Validacion
        DatoMandatorioOmitidoException thrown = assertThrows(DatoMandatorioOmitidoException.class, () -> {
            servicioRegistrarPaciente.registrarPaciente(dto);
        });
        assertEquals("La localidad es obligatoria", thrown.getMessage());

        verifyNoInteractions(servicioObraSocial);
        verifyNoInteractions(repositorioPacientes);
    }

}