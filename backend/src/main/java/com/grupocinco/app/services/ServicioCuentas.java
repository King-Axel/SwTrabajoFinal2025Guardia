package com.grupocinco.app.services;

import com.grupocinco.app.interfaces.IRepositorioCuentas;
import com.grupocinco.domain.Cuenta;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioCuentas {
    private final IRepositorioCuentas IRepositorioCuentas;

    public ServicioCuentas(IRepositorioCuentas IRepositorioCuentas) {
        this.IRepositorioCuentas = IRepositorioCuentas;
    }

    public Optional<Cuenta> buscarPorEmail(String email) {
        return IRepositorioCuentas.findByEmail(email);
    }
}
