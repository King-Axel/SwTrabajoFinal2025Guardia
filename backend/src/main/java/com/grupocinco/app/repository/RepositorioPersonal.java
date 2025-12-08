package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.IRepositorioPersonal;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioPersonal implements IRepositorioPersonal {
    private final List<Persona> dbPersonal = new ArrayList<>();

    @Override
    public void save(Persona persona) {
        dbPersonal.add(persona);
    }

    @Override
    public List<Persona> findAll() {
        return dbPersonal;
    }

    @Override
    public Optional<Persona> findByCuil(String cuil) {
        return dbPersonal.stream()
                .filter(p -> p.getCuil().equals(cuil))
                .findFirst();
    }
}
