package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RepositorioCuentas {
    Optional<Cuenta> buscar(String email);
    boolean guardar(Cuenta cuenta);
}
