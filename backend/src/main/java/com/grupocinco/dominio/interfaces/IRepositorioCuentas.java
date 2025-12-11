package com.grupocinco.dominio.interfaces;

import com.grupocinco.dominio.Cuenta;

import java.util.Optional;

public interface IRepositorioCuentas {
    Optional<Cuenta> findByEmail(String email);
    void save(Cuenta cuenta);
}
