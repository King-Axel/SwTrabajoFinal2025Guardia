package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Cuenta;

import java.util.Optional;

public interface IRepositorioCuentas {
    Optional<Cuenta> findByEmail(String email);
    void save(Cuenta cuenta);
}
