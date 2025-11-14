package com.grupocinco.app;

import com.grupocinco.app.exceptions.CredencialesInvalidasException;
import com.grupocinco.app.exceptions.CuentaExistenteException;
import com.grupocinco.app.exceptions.InconsistenciaRolPersonalException;
import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioAutenticacionTest {
    private ServicioAutenticacion servicio;
    private RepositorioCuentas repositorio;

    @BeforeEach
    public void setUp() {
        this.repositorio = mock(RepositorioCuentas.class); // Simular una DB que implemente RepositorioCuentas

        this.servicio = new ServicioAutenticacion(repositorio);
    }

    @Test
    public void autenticacionCorrectaConCredencialesValidas() {
        // Preparacion
        String email = "test@test.com";
        String contrasena = "contrasena";

        when(repositorio.buscar(email)).thenReturn(Optional.of(
                new Cuenta(
                    Email.of(email),
                    Contrasena.fromRaw(contrasena),
                    Rol.ENFERMERA,
                    new Enfermera("Lopez","Juana")
                )
            )
        );

        // Ejecucion y Validacion
        assertThat(servicio.iniciarSesion(email, contrasena)).isTrue();
        verify(repositorio, times(1)).buscar(email);
    }

    @Test
    public void autenticacionIncorrectaConEmailInvalidoDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        // Preparacion
        String email = "text@test.com";
        String contrasena = "contrasena";

        when(repositorio.buscar(email)).thenReturn(Optional.empty());

        // Ejecucion y Validacion
        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
        .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos");
        verify(repositorio, times(1)).buscar(email);
    }

    @Test
    public void autenticacionIncorrectaConContrasenaInvalidaDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        // Preparacion
        String email = "test@test.com";
        String contrasena = "contrasenia";
        Cuenta cuenta = new Cuenta(
                Email.of(email),
                Contrasena.fromRaw("contrasena"),
                Rol.ENFERMERA,
                new Enfermera("Lopez","Juana")
        );

        when(repositorio.buscar(email)).thenReturn(Optional.of(cuenta));

        // Ejecucion y Validacion
        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
                .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos");
        verify(repositorio, times(1)).buscar(email);
    }

    @Test
    public void registroCorrectoDeNuevaCuenta() {
        // Preparacion
        String email = "correo@gmail.com";
        String contrasena = "contrasenahipersegura";
        Rol rol = Rol.MEDICO;
        Medico medico = new Medico("Rivas", "Julia", "9898989898");

        Cuenta cuenta =
                new Cuenta(Email.of(email), Contrasena.fromRaw(contrasena), rol, medico);

        when(repositorio.guardar(cuenta)).thenReturn(true);

        // Ejecucion
        boolean ok = servicio.registrar(cuenta);

        // Validacion
        assertThat(ok).isTrue();
        verify(repositorio, times(1)).buscar(email);
        verify(repositorio, times(1)).guardar(cuenta);
    }

    @Test
    public void tratarDeRegistrarUnaCuentaConEmailYaUtilizadoEnOtraCuentaDeberiaGenerarExcepcionDeCuentaExistente() {
        // Preparacion
        String email= "test@test.com";
        String contrasena = "clavesegura22";
        Rol rol = Rol.MEDICO;
        Medico medico = new Medico("Moreira", "Roberto", "1212121212");

        Cuenta cuenta = new Cuenta(Email.of(email), Contrasena.fromRaw(contrasena), rol, medico);

        when(repositorio.buscar(email)).thenReturn(Optional.of(
                new Cuenta(
                        Email.of(email),
                        Contrasena.fromRaw("contrasena"),
                        Rol.ENFERMERA,
                        new Enfermera("Lopez","Juana")
                )
        ));

        // Ejecucion y Validacion
        assertThatThrownBy(() -> servicio.registrar(cuenta))
                .isExactlyInstanceOf(CuentaExistenteException.class)
                .hasMessage("Ya existe una cuenta con el email ingresado");
        verify(repositorio, times(1)).buscar(email);
    }

    @Test
    public void tratarDeRegistrarUnaCuentaConRolEnfermeraParaUnMedicoDeberiaLanzarUnaExcepcion() {
        // Preparacion
        Email email = Email.of("email@dominio.com");
        Contrasena contrasena = Contrasena.fromRaw("12GuisoDeFideo_2002");
        Rol rol = Rol.ENFERMERA;
        Medico medico = new Medico("Gomez", "Reberto", "8787121255");

        // Ejecucion y Validacion
        assertThatThrownBy(() -> new Cuenta(email, contrasena, rol, medico))
                .isExactlyInstanceOf(InconsistenciaRolPersonalException.class)
                .hasMessage("El rol ENFERMERA no es compatible con el tipo de personal Medico");
    }

    @Test
    public void tratarDeRegistrarUnaCuentaConRolMedicoParaUnaEnfermeraDeberiaLanzarUnaExcepcion() {
        // Preparacion
        Email email = Email.of("email@dominio.com");
        Contrasena contrasena = Contrasena.fromRaw("12GuisoDeFideo_2002");
        Rol rol = Rol.MEDICO;
        Enfermera enfermera = new Enfermera("Lopez", "Jacinta");

        // Ejecucion y Validacion
        assertThatThrownBy(() -> new Cuenta(email, contrasena, rol, enfermera))
                .isExactlyInstanceOf(InconsistenciaRolPersonalException.class)
                .hasMessage("El rol MEDICO no es compatible con el tipo de personal Enfermera");
    }
}
