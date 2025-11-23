package com.grupocinco.app;

import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.exceptions.CredencialesInvalidasException;
import com.grupocinco.app.exceptions.CuentaExistenteException;
import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioAutenticacionTest {
    private ServicioAutenticacion servicio;
    private RepositorioCuentas repositorio;
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        this.repositorio = mock(RepositorioCuentas.class);
        this.encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);
        this.servicio = new ServicioAutenticacion(repositorio, encoder);
    }

    @Test
    public void autenticacionCorrectaConCredencialesValidas() {
        String email = "test@test.com";
        String contrasena = "contrasena";

        Cuenta cuenta = new Cuenta(
                Email.of(email),
                Contrasena.of(encoder.encode(contrasena)),
                Rol.ENFERMERA,
                new Enfermera("Lopez","Juana")
        );

        when(repositorio.buscarPorEmail(email)).thenReturn(Optional.of(cuenta));

        assertThat(servicio.iniciarSesion(email, contrasena)).isEqualTo(cuenta);
        verify(repositorio, times(1)).buscarPorEmail(email);
    }

    @Test
    public void autenticacionIncorrectaConEmailInvalidoDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        String email = "text@test.com";
        String contrasena = "contrasena";

        when(repositorio.buscarPorEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
        .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos");
        verify(repositorio, times(1)).buscarPorEmail(email);
    }

    @Test
    public void autenticacionIncorrectaConContrasenaInvalidaDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        String email = "test@test.com";
        String contrasena = "contrasenia";

        Cuenta cuenta = new Cuenta(
                Email.of(email),
                Contrasena.of(encoder.encode("contrasena")),
                Rol.ENFERMERA,
                new Enfermera("Lopez","Juana")
        );

        when(repositorio.buscarPorEmail(email)).thenReturn(Optional.of(cuenta));

        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
                .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos");
        verify(repositorio, times(1)).buscarPorEmail(email);
    }

    @Test
    public void registroCorrectoDeNuevaCuenta() {
        String email = "correo@gmail.com";
        String contrasena = "contrasenahipersegura";
        String rol = Rol.MEDICO.name();

        PersonaDTO persona = new PersonaDTO();
        persona.setApellido("Rivas");
        persona.setNombre("Julia");
        persona.setMatricula("9898989898");

        when(repositorio.buscarPorEmail(email)).thenReturn(Optional.empty());

        assertThatCode(
                () -> servicio.registrar(email, contrasena, rol, persona)
        ).doesNotThrowAnyException();

        verify(repositorio, times(1)).buscarPorEmail(email);
        verify(repositorio, times(1))
                .guardar(argThat(cuenta ->
                    cuenta.getEmail().equals(email)
                        && encoder.matches(contrasena, cuenta.getContrasena())
                        && cuenta.getRol() == Rol.MEDICO
                        && cuenta.getPersona() instanceof Medico
                        && ((Medico) cuenta.getPersona()).getMatricula().equals("9898989898")
                ));
    }

    @Test
    public void tratarDeRegistrarUnaCuentaConEmailYaUtilizadoEnOtraCuentaDeberiaGenerarExcepcionDeCuentaExistente() {
        String email= "test@test.com";
        String contrasena = "";
        String rol = Rol.MEDICO.name();

        PersonaDTO persona = new PersonaDTO();
        persona.setApellido("Moreira");
        persona.setNombre("Roberto");
        persona.setMatricula("1212121212");

        when(repositorio.buscarPorEmail(email)).thenReturn(Optional.of(
                new Cuenta(
                        Email.of(email),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.ENFERMERA,
                        new Enfermera("Lopez","Juana")
                )
        ));

        assertThatThrownBy(() -> servicio.registrar(email, contrasena, rol, persona))
                .isExactlyInstanceOf(CuentaExistenteException.class)
                .hasMessage("Ya existe una cuenta con el email ingresado");
        verify(repositorio, times(1)).buscarPorEmail(email);
    }
}
