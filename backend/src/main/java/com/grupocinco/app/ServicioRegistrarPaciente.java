package com.grupocinco.app;

import com.grupocinco.app.dtos.PacienteDTO;
import com.grupocinco.app.exceptions.DatoMandatorioOmitidoException;
import com.grupocinco.app.exceptions.ObraSocialInexistenteException;
import com.grupocinco.app.exceptions.PacienteNoAfiliadoException;
import com.grupocinco.app.interfaces.RepositorioObrasSociales;
import com.grupocinco.app.interfaces.RepositorioPacientes;
import com.grupocinco.app.mappers.PacienteMapper;
import com.grupocinco.domain.Afiliado;
import com.grupocinco.domain.ObraSocial;
import com.grupocinco.domain.Paciente;
import java.util.Optional;

public class ServicioRegistrarPaciente {
    private final RepositorioPacientes repositorioPacientes;
    private final RepositorioObrasSociales repositorioObrasSociales;

    public ServicioRegistrarPaciente(RepositorioPacientes repositorioPacientes, RepositorioObrasSociales repositorioObrasSociales) {
        this.repositorioPacientes = repositorioPacientes;
        this.repositorioObrasSociales = repositorioObrasSociales;
    }

    public Paciente registrarPaciente(PacienteDTO dto) {
        if (dto == null) throw new DatoMandatorioOmitidoException("Los datos del paciente son obligatorios");

        if (dto.getCuil() == null || dto.getCuil().isBlank())
            throw new DatoMandatorioOmitidoException("Falta el dato CUIL");

        if (repositorioPacientes.findByCuil(dto.getCuil()).isPresent())
            throw new IllegalArgumentException("El paciente ya existe");

        Afiliado afiliado;

        if (dto.getAfiliado() != null) {
            ObraSocial obraSocial = repositorioObrasSociales.findById(dto.getAfiliado().getObraSocial().getId()).orElseThrow(
                    () -> new ObraSocialInexistenteException("Obra social inexistente")
            );

            afiliado = new Afiliado(obraSocial, dto.getAfiliado().getNumeroAfiliado());
            boolean afiliacion = repositorioObrasSociales.isAfiliated(afiliado);

            if (!afiliacion) {
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

}