package com.grupocinco.app.repository;

import com.grupocinco.app.interfaces.RepositorioPersonal;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioDePersonal implements RepositorioPersonal {
    List<Persona> dbPersonal = new ArrayList<>();

    public RepositorioDePersonal() {
        dbPersonal.add(new Enfermera("Lopez","Jacinta Maria"));
        dbPersonal.add(new Enfermera("Pérez","Ana Lucía"));
        dbPersonal.add(new Medico("Gomez","Carlos Alberto","1212212121"));
        dbPersonal.add(new Medico("Rivas","Julia","9898989898"));
    }

    @Override
    public List<Persona> findAll() {
        return dbPersonal;
    }
}
