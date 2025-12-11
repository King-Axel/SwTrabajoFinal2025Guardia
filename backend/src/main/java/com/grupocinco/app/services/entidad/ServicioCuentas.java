package com.grupocinco.app.services.entidad;

import com.grupocinco.dominio.interfaces.IRepositorioCuentas;
import com.grupocinco.dominio.Cuenta;
import org.springframework.stereotype.Service;

@Service
public class ServicioCuentas {
    private final IRepositorioCuentas IRepositorioCuentas;

    public ServicioCuentas(IRepositorioCuentas IRepositorioCuentas) {
        this.IRepositorioCuentas = IRepositorioCuentas;
    }

    public Cuenta buscarPorEmail(String email) {
        return IRepositorioCuentas.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
