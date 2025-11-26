package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.RepositorioPersonal;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioDePersonal implements RepositorioPersonal {
    private final List<Persona> dbPersonal = new ArrayList<>();

    public RepositorioDePersonal() {
        dbPersonal.add(new Enfermera("Lopez","Jacinta Maria", "27-23589461-0"));
        dbPersonal.add(new Enfermera("Pérez","Ana Lucía", "27-24569741-0"));
        dbPersonal.add(new Medico("Gomez", "Carlos Alberto", "1212212121", "20-31223344-8"));
        dbPersonal.add(new Medico("Rivas", "Julia", "9898989898", "27-40991234-6"));
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
