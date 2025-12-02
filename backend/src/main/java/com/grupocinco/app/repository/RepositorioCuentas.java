package com.grupocinco.app.repository;


import com.grupocinco.app.interfaces.IRepositorioCuentas;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Cuenta;
import com.grupocinco.domain.valueobject.Contrasena;
import com.grupocinco.domain.valueobject.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RepositorioCuentas implements IRepositorioCuentas {
    private final Map<String, Cuenta> dbCuenta = new HashMap<>();

    public RepositorioCuentas(PasswordEncoder encoder, RepositorioPersonal dbPersonal) {
        dbCuenta.put(
                "lopezjacinta@gmail.com",
                new Cuenta(Email.of("lopezjacinta@gmail.com"),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.ENFERMERA,
                        dbPersonal.findByCuil("27-23589461-0").get()
                )
        );
        dbCuenta.put(
                "gomezcarlos@gmail.com",
                new Cuenta(Email.of("gomezcarlos@gmail.com"),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.MEDICO,
                        dbPersonal.findByCuil("27-40991234-6").get()
                )
        );
    }

    @Override
    public Optional<Cuenta> findByEmail(String email) {
        return Optional.ofNullable(dbCuenta.get(email));
    }

    @Override
    public void save(Cuenta cuenta) {
        dbCuenta.put(cuenta.getEmail(), cuenta);
    }
}
