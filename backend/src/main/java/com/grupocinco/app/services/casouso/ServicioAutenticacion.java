package com.grupocinco.app.services.casouso;

import com.grupocinco.dominio.exceptions.CredencialesInvalidasException;
import com.grupocinco.dominio.exceptions.CuentaExistenteException;
import com.grupocinco.dominio.exceptions.PersonalInexistenteException;
import com.grupocinco.dominio.interfaces.IRepositorioCuentas;
import com.grupocinco.dominio.interfaces.IRepositorioPersonal;
import com.grupocinco.app.infraestructura.util.Rol;
import com.grupocinco.dominio.Cuenta;
import com.grupocinco.dominio.Persona;
import com.grupocinco.dominio.valueobject.Contrasena;
import com.grupocinco.dominio.valueobject.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion {
    private final IRepositorioCuentas repositorioCuentas;
    private final PasswordEncoder encoder;
    private final IRepositorioPersonal repositorioPersonal;

    public ServicioAutenticacion(IRepositorioCuentas repositorioCuentas, PasswordEncoder encoder, IRepositorioPersonal repositorioPersonal) {
        this.repositorioCuentas = repositorioCuentas;
        this.encoder = encoder;
        this.repositorioPersonal = repositorioPersonal;
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

    public void registrar(String emailReq, String contrasenaReq, String rolReq, String cuilReq) {
        Email email = Email.of(emailReq);

        if(repositorioCuentas.findByEmail(email.get()).isPresent()) {
            throw new CuentaExistenteException();
        } else {
            Contrasena.validarRaw(contrasenaReq);
            Contrasena contrasena = Contrasena.of(encoder.encode(contrasenaReq));
            Rol rol = Rol.desdeString(rolReq);
            Persona persona = repositorioPersonal.findByCuil(cuilReq).orElseThrow(
                    () -> new PersonalInexistenteException("No existe personal con el cuil " + cuilReq)
            );

            Cuenta cuenta = new Cuenta(email, contrasena, rol, persona);

            repositorioCuentas.save(cuenta);
        }
    }
}
