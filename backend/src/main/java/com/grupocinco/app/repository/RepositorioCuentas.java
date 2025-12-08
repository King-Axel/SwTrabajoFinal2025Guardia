package com.grupocinco.app.repository;


import com.grupocinco.app.interfaces.IRepositorioCuentas;
import com.grupocinco.domain.Cuenta;
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
