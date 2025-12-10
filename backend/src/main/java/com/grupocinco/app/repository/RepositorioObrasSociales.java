package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioObrasSociales;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioObrasSociales implements IRepositorioObrasSociales {
    private final Map<UUID, ObraSocial> obrasSociales = new HashMap<>();
    private final Map<String, Afiliado> afiliaciones = new HashMap<>();

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
    public void save(ObraSocial obraSocial) {
        obrasSociales.put(obraSocial.getId(), obraSocial);
    }

    @Override
    public List<ObraSocial> findAll() {
        return obrasSociales.values().stream().toList();
    }

    // Afiliaciones
    @Override
    public void saveAfiliado(Afiliado afiliado) {
        afiliaciones.put(afiliado.getNumeroAfiliado(), afiliado);
    }

    @Override
    public Optional<Afiliado> findAfiliadoByNumeroAfiliado(String numero) {
        return Optional.ofNullable(afiliaciones.get(numero));
    }

    @Override
    public boolean isAfiliated(Afiliado afiliado) {
        Afiliado encontrado = afiliaciones.get(afiliado.getNumeroAfiliado());

        return encontrado != null &&
                encontrado.getObraSocial().getId().equals(afiliado.getObraSocial().getId());
    }
}
