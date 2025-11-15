package org.example.app.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.app.interfaces.ServicioObraSocial;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito.*;

class ServicioRegistrarPacienteTest {
    private ServicioRegistrarPaciente servicioRegistrarPaciente;
    private RepositorioPacientes repositorioPacientes;
    private ServicioObraSocial servicioObraSocial;

    @BeforeEach
    void setUp() {
        this.repositorioPacientes = mock(RepositorioPacientes.class);
        this.servicioObraSocial = mock(ServicioObraSocial.class);
    }

}