package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RepositorioCuentas {
    Optional<Cuenta> buscarPorEmail(String email);
    void guardar(Cuenta cuenta);
}
