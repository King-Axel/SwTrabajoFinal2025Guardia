package com.grupocinco.app;

import com.grupocinco.app.exceptions.CredencialesInvalidasException;
import com.grupocinco.app.exceptions.CuentaExistenteException;
import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.app.interfaces.RepositorioPersonal;
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
    private RepositorioPersonal repositorioPersonal; // Nuevo mock
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        this.repositorio = mock(RepositorioCuentas.class);
        this.repositorioPersonal = mock(RepositorioPersonal.class); // Inicializar mock
        this.encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);
        
        // Ahora pasamos los 3 argumentos que pide el constructor real
        this.servicio = new ServicioAutenticacion(repositorio, encoder, repositorioPersonal);
    }

    @Test
    public void autenticacionCorrectaConCredencialesValidas() {
        String email = "test@test.com";
        String contrasena = "contrasena";

        Cuenta cuenta = new Cuenta(
                Email.of(email),
                Contrasena.of(encoder.encode(contrasena)),
                Rol.ENFERMERA,
                new Enfermera("Lopez","Juana", "27-41234567-6")
        );

        when(repositorio.findByEmail(email)).thenReturn(Optional.of(cuenta));

        assertThat(servicio.iniciarSesion(email, contrasena)).isEqualTo(cuenta);
        verify(repositorio, times(1)).findByEmail(email);
    }

    @Test
    public void autenticacionIncorrectaConEmailInvalidoDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        String email = "text@test.com";
        String contrasena = "contrasena";

        when(repositorio.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
                .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos"); // Nota: Verifica que este mensaje coincida con tu excepción real
        verify(repositorio, times(1)).findByEmail(email);
    }

    @Test
    public void autenticacionIncorrectaConContrasenaInvalidaDeberiaGenerarExcepcionDeCredencialesInvalidas() {
        String email = "test@test.com";
        String contrasena = "contrasenia";

        Cuenta cuenta = new Cuenta(
                Email.of(email),
                Contrasena.of(encoder.encode("contrasena")),
                Rol.ENFERMERA,
                new Enfermera("Lopez","Juana", "27-41234567-6")
        );

        when(repositorio.findByEmail(email)).thenReturn(Optional.of(cuenta));

        assertThatThrownBy(() -> servicio.iniciarSesion(email, contrasena))
                .isExactlyInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Usuario o contraseña inválidos");
        verify(repositorio, times(1)).findByEmail(email);
    }

    @Test
    public void registroCorrectoDeNuevaCuenta() {
        String email = "correo@gmail.com";
        String contrasena = "contrasenahipersegura";
        String rol = Rol.MEDICO.name();
        String cuil = "27-41235567-6"; // Usamos String CUIL directamente

        // Simulamos que el médico YA existe en la base de datos de personal (requisito de tu servicio)
        Medico medicoExistente = new Medico("Rivas", "Julia", cuil, "9898989898");
        when(repositorioPersonal.findByCuil(cuil)).thenReturn(Optional.of(medicoExistente));

        when(repositorio.findByEmail(email)).thenReturn(Optional.empty());

        // Pasamos el CUIL en lugar del objeto PersonaDTO
        assertThatCode(
                () -> servicio.registrar(email, contrasena, rol, cuil)
        ).doesNotThrowAnyException();

        verify(repositorio, times(1)).findByEmail(email);
        verify(repositorio, times(1))
                .save(argThat(cuenta ->
                    cuenta.getEmail().equals(email)
                        && encoder.matches(contrasena, cuenta.getContrasena())
                        && cuenta.getRol() == Rol.MEDICO
                        && cuenta.getPersona().getCuil().equals(cuil)
                ));
    }

    @Test
    public void tratarDeRegistrarUnaCuentaConEmailYaUtilizadoEnOtraCuentaDeberiaGenerarExcepcionDeCuentaExistente() {
        String email= "test@test.com";
        String contrasena = "";
        String rol = Rol.MEDICO.name();
        String cuil = "27-12121212-6"; // String CUIL dummy

        when(repositorio.findByEmail(email)).thenReturn(Optional.of(
                new Cuenta(
                        Email.of(email),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.ENFERMERA,
                        new Enfermera("Lopez","Juana", "27-41234567-6")
                )
        ));

        // Pasamos el CUIL string
        assertThatThrownBy(() -> servicio.registrar(email, contrasena, rol, cuil))
                .isExactlyInstanceOf(CuentaExistenteException.class)
                .hasMessage("Ya existe una cuenta con el email ingresado"); // Verifica este mensaje
        verify(repositorio, times(1)).findByEmail(email);
    }
}