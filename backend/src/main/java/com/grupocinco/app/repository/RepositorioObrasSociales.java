package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioObrasSociales;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepositorioObrasSociales implements IRepositorioObrasSociales {
    @Override
    public Optional<ObraSocial> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ObraSocial> findByName(String nombreObraSocial) {
        return Optional.empty();
    }

    @Override
    public boolean isAfiliated(Afiliado afiliado) {
        return false;
    }

}
