package com.grupocinco.app.repository;


import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.domain.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RepositorioDeCuentas implements RepositorioCuentas {
    private final Map<String, Cuenta> dbCuenta = new HashMap<>();

    @Override
    public Optional<Cuenta> buscar(String email) {
        return Optional.ofNullable(dbCuenta.get(email));
    }

    @Override
    public boolean guardar(Cuenta cuenta) {
        if (dbCuenta.containsKey(cuenta.getEmail())) return false;
        dbCuenta.put(cuenta.getEmail(), cuenta);
        return true;
    }
}
