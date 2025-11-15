package org.example.app.servicio;

import org.example.app.dtos.RegistroPacienteDTO;
import org.example.app.interfaces.*;
import org.example.domain.*;
import org.example.app.exceptions.*;

public class ServicioRegistrarPaciente {
    private final RepositorioPacientes repositorioPacientes;
    private final ServicioObraSocial servicioObraSocial;

    public ServicioRegistrarPaciente(RepositorioPacientes repositorioPacientes, ServicioObraSocial servicioObraSocial) {
        this.repositorioPacientes = repositorioPacientes;
        this.servicioObraSocial = servicioObraSocial;
    }

    public Paciente registrarPaciente(RegistroPacienteDTO dto) {
        validarDatosMandatorios(dto);
        validarFormatoCuil(dto.getCuil());
        AfiliacionObraSocial afiliacionObraSocial = null;

        if (dto.getIdObraSocial() != null) {
            if (dto.getNumeroAfiliado() == null || dto.getNumeroAfiliado().isBlank()) {
                throw new DatoMandatorioOmitidoException(
                        "El numero de afiliado es obligatorio cuando se informa una obra social"
                );
            }

            afiliacionObraSocial = servicioObraSocial.obtenerObraSocialPorCodigo(dto.getIdObraSocial());
            if (afiliacionObraSocial == null) {
                throw new ObraSocialInexistenteException("Obra social inexistente");
            }

            boolean afiliado = servicioObraSocial.estaAfiliado(afiliacionObraSocial, dto.getNumeroAfiliado());
            if (!afiliado) {
                throw new PacienteNoAfiliadoException("El paciente no esta afiliado a la obra social");
            }
        }

        Domicilio domicilio = new Domicilio(
                dto.getCalle(),
                dto.getNumero(),
                dto.getLocalidad()
        );

        Paciente paciente = new Paciente(
                dto.getCuil(),
                dto.getApellido(),
                dto.getNombre(),
                domicilio,
                afiliacionObraSocial
        );

        repositorioPacientes.registrarPaciente(dto.getCuil(), paciente);

        return paciente;
    }

    private void validarDatosMandatorios(RegistroPacienteDTO dto) {
        if (dto == null) {
            throw new DatoMandatorioOmitidoException("Los datos del paciente son obligatorios");
        }

        if (esNuloOVacio(dto.getCuil())) {
            throw new DatoMandatorioOmitidoException("El cuil es obligatorio");
        }
        if (esNuloOVacio(dto.getApellido())) {
            throw new DatoMandatorioOmitidoException("El apellido es obligatorio");
        }
        if (esNuloOVacio(dto.getNombre())) {
            throw new DatoMandatorioOmitidoException("El nombre es obligatorio");
        }
        if (esNuloOVacio(dto.getCalle())) {
            throw new DatoMandatorioOmitidoException("La calle es obligatoria");
        }
        if (dto.getNumero() == null) {
            throw new DatoMandatorioOmitidoException("El numero de domicilio es obligatorio");
        }
        if (esNuloOVacio(dto.getLocalidad())) {
            throw new DatoMandatorioOmitidoException("La localidad es obligatoria");
        }
    }

    private boolean esNuloOVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    private void validarFormatoCuil(String cuil) {
        if (cuil == null) {
            throw new CuilInvalidoException("Cuil invalido (es null)");
        }

        String regex = "\\d{2}-\\d{8}-\\d";
        if (!cuil.matches(regex)) {
            throw new CuilInvalidoException("Cuil invalido (formato esperado: XX-XXXXXXXX-X)");
        }
    }
}
