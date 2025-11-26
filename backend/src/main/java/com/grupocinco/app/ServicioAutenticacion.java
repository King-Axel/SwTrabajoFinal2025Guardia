package com.grupocinco.app;

import com.grupocinco.app.dtos.PersonaDTO;
import com.grupocinco.app.exceptions.CredencialesInvalidasException;
import com.grupocinco.app.exceptions.CuentaExistenteException;
import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.app.mappers.PersonaMapper;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.Persona;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion {
    private final RepositorioCuentas repositorioCuentas;
    private final PasswordEncoder encoder;

    public ServicioAutenticacion(RepositorioCuentas repositorioCuentas, PasswordEncoder encoder) {
        this.repositorioCuentas = repositorioCuentas;
        this.encoder = encoder;
    }

    public Cuenta iniciarSesion(String emailReq, String contrasenaReq) {
        Email email = Email.of(emailReq);

        Cuenta cuenta = repositorioCuentas.findByEmail(email.get()).orElse(null);

        if (
                cuenta == null ||
                !encoder.matches(contrasenaReq, cuenta.getContrasena())
        ) throw new CredencialesInvalidasException();

        return cuenta;
    }

    public void registrar(String emailReq, String contrasenaReq, String rolReq, PersonaDTO personaReq) {
        Email email = Email.of(emailReq);

        if(repositorioCuentas.findByEmail(email.get()).isPresent()) {
            throw new CuentaExistenteException();
        } else {
            Contrasena.validarRaw(contrasenaReq);
            Contrasena contrasena = Contrasena.of(encoder.encode(contrasenaReq));
            Rol rol = Rol.desdeString(rolReq);
            Persona persona = PersonaMapper.desdeDTO(personaReq);

            Cuenta cuenta = new Cuenta(email, contrasena, rol, persona);

            repositorioCuentas.save(cuenta);
        }
    }
}
