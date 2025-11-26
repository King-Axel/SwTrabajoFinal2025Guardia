package com.grupocinco.app.services;

import com.grupocinco.app.interfaces.RepositorioCuentas;
import com.grupocinco.domain.Cuenta;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioCuentas {
    private final RepositorioCuentas repositorioCuentas;

    public ServicioCuentas(RepositorioCuentas repositorioCuentas) {
        this.repositorioCuentas = repositorioCuentas;
    }

    public Optional<Cuenta> buscarPorEmail(String email) {
        return repositorioCuentas.findByEmail(email);
    }
}
