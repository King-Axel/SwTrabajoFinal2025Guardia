package com.grupocinco.app.infraestructura.repository;


import com.grupocinco.dominio.interfaces.IRepositorioCuentas;
import com.grupocinco.dominio.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RepositorioCuentas implements IRepositorioCuentas {
    private final Map<String, Cuenta> dbCuenta = new HashMap<>();

    @Override
    public Optional<Cuenta> findByEmail(String email) {
        return Optional.ofNullable(dbCuenta.get(email));
    }

    @Override
    public void save(Cuenta cuenta) {
        dbCuenta.put(cuenta.getEmail(), cuenta);
    }
}
