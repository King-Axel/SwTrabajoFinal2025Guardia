package com.grupocinco.app.interfaces;

import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepositorioObrasSociales {
    Optional<ObraSocial> findById(UUID id);
    @Deprecated
    Optional<ObraSocial> findByName(String nombreObraSocial);
    boolean isAfiliated(Afiliado afiliado);
    void save(ObraSocial obrasSocial);
    List<ObraSocial> findAll();
}
