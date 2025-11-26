package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Cuenta;

import java.util.Optional;

public interface RepositorioCuentas {
    Optional<Cuenta> findByEmail(String email);
    void save(Cuenta cuenta);
}
