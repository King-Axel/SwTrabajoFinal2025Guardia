package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioObrasSociales;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioObrasSociales implements IRepositorioObrasSociales {
    private final Map<UUID, ObraSocial> obrasSociales = new HashMap<>();

    @Override
    public Optional<ObraSocial> findById(UUID id) {
        return Optional.ofNullable(obrasSociales.get(id));
    }

    @Override
    public Optional<ObraSocial> findByName(String nombreObraSocial) {
        return obrasSociales.values()
                        .stream()
                        .filter(os -> os.getNombre().equalsIgnoreCase(nombreObraSocial))
                        .findFirst()
        ;
    }

    @Override
    public boolean isAfiliated(Afiliado afiliado) {
        return false;
    }

    @Override
    public void save(ObraSocial obraSocial) {
        obrasSociales.put(obraSocial.getId(), obraSocial);
    }

    @Override
    public List<ObraSocial> findAll() {
        return new ArrayList<>(obrasSociales.values());
    }
}
