package com.grupocinco.app.services.casouso;

import com.grupocinco.app.infraestructura.dtos.PacienteDTO;
import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import com.grupocinco.dominio.exceptions.ObraSocialInexistenteException;
import com.grupocinco.dominio.exceptions.PacienteNoAfiliadoException;
import com.grupocinco.dominio.interfaces.IRepositorioObrasSociales;
import com.grupocinco.dominio.interfaces.IRepositorioPacientes;
import com.grupocinco.app.infraestructura.mappers.PacienteMapper;
import com.grupocinco.dominio.Afiliado;
import com.grupocinco.dominio.ObraSocial;
import com.grupocinco.dominio.Paciente;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ServicioRegistrarPaciente {
    private final IRepositorioPacientes repositorioPacientes;
    private final IRepositorioObrasSociales repositorioObrasSociales;

    public ServicioRegistrarPaciente(IRepositorioPacientes repositorioPacientes, IRepositorioObrasSociales repositorioObrasSociales) {
        this.repositorioPacientes = repositorioPacientes;
        this.repositorioObrasSociales = repositorioObrasSociales;
    }

    public Paciente registrarPaciente(PacienteDTO dto) {
        if (dto == null) throw new DatoMandatorioOmitidoException("Los datos del paciente son obligatorios");

        if (repositorioPacientes.findByCuil(dto.getCuil()).isPresent())
            throw new IllegalArgumentException("El paciente ya existe");

        if (dto.getAfiliado() != null) {
            ObraSocial obraSocial = repositorioObrasSociales.findByName(dto.getAfiliado().getObraSocial().getNombre())
                    .orElseThrow(() -> new ObraSocialInexistenteException("Obra social inexistente"));

            Afiliado afiliado = new Afiliado(obraSocial, dto.getAfiliado().getNumeroAfiliado());
            boolean tieneAfiliacion = repositorioObrasSociales.isAfiliated(afiliado);

            if (!tieneAfiliacion) {
                throw new PacienteNoAfiliadoException("El paciente no esta afiliado a la obra social");
            }
        }

        Paciente paciente = PacienteMapper.desdeDTO(dto);

        repositorioPacientes.save(paciente);

        return paciente;
    }

    public Optional<Paciente> buscarPorCuil(String cuil) {
        if (cuil == null || cuil.isBlank()) {
            throw new DatoMandatorioOmitidoException("Falta el dato CUIL");
        }
        return repositorioPacientes.findByCuil(cuil);
    }

    public List<PacienteDTO> obtenerTodos() {
        return repositorioPacientes.findAll().stream().map(PacienteMapper::aDTO).toList();
    }
}