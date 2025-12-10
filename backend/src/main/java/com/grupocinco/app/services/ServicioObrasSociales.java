package com.grupocinco.app.services;

import com.grupocinco.app.dtos.ObraSocialDTO;
import com.grupocinco.app.interfaces.IRepositorioObrasSociales;
import com.grupocinco.app.mappers.ObraSocialMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioObrasSociales {
    private final IRepositorioObrasSociales repositorioObrasSociales;

    public ServicioObrasSociales(IRepositorioObrasSociales repositorioObrasSociales) {
        this.repositorioObrasSociales = repositorioObrasSociales;
    }

    public List<ObraSocialDTO> obtenerTodas() {
        return repositorioObrasSociales.findAll().stream().map(ObraSocialMapper::aDTO).toList();
    }
}
