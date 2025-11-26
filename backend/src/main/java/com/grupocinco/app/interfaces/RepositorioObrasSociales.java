package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;

import java.util.Optional;

public interface RepositorioObrasSociales {
    Optional<ObraSocial> findById(Long id);

    @Deprecated
    Optional<ObraSocial> findByName(String nombreObraSocial);

    boolean isAfiliated(Afiliado afiliado);
}
