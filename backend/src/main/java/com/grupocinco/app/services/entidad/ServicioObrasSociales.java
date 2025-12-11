package com.grupocinco.app.services.entidad;

import com.grupocinco.app.infraestructura.dtos.ObraSocialDTO;
import com.grupocinco.dominio.interfaces.IRepositorioObrasSociales;
import com.grupocinco.app.infraestructura.mappers.ObraSocialMapper;
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
