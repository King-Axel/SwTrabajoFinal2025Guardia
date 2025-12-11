package com.grupocinco.dominio.interfaces;

import com.grupocinco.dominio.Afiliado;
import com.grupocinco.dominio.ObraSocial;

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
    void saveAfiliado(Afiliado afiliado);
    Optional<Afiliado> findAfiliadoByNumeroAfiliado(String numeroAfiliado);
}
