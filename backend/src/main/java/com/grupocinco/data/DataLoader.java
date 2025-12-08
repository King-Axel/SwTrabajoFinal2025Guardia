package com.grupocinco.data;

import com.grupocinco.app.repository.RepositorioCuentas;
import com.grupocinco.app.repository.RepositorioObrasSociales;
import com.grupocinco.app.repository.RepositorioPacientes;
import com.grupocinco.app.repository.RepositorioPersonal;
import org.springframework.boot.CommandLineRunner;

public class DataLoader implements CommandLineRunner {
    private final RepositorioCuentas repositorioCuentas;
    private final RepositorioObrasSociales repositorioObrasSociales;
    private final RepositorioPacientes repositorioPacientes;
    private final RepositorioPersonal repositorioPersonal;

    public DataLoader(
            RepositorioObrasSociales repositorioObrasSociales
    ) {
        this.repositorioObrasSociales = repositorioObrasSociales;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
