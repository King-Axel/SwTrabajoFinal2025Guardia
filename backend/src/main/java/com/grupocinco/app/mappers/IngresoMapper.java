package com.grupocinco.app.mappers;

import com.grupocinco.app.dtos.IngresoDTO;
import com.grupocinco.domain.Ingreso;

public class IngresoMapper {
    public static IngresoDTO aDTO(Ingreso ingreso) {
        IngresoDTO ingresoDTO = new IngresoDTO();

        ingresoDTO.setId(ingreso.getId());
        ingresoDTO.setPaciente(PacienteMapper.aDTO(ingreso.getPaciente()));
        ingresoDTO.setInforme(ingreso.getInforme());
        ingresoDTO.setNivelEmergencia(ingreso.getNivelEmergencia().name());
        ingresoDTO.setEstado(ingreso.getEstado());
        ingresoDTO.setTemperatura(String.valueOf(ingreso.getTemperatura().getTemperatura()));
        ingresoDTO.setFrecuenciaCardiaca(String.valueOf(ingreso.getFrecuenciaCardiaca().getFrecuencia()));
        ingresoDTO.setFrecuenciaRespiratoria(String.valueOf(ingreso.getFrecuenciaRespiratoria().getFrecuencia()));
        ingresoDTO.setFrecuenciaSistolica(String.valueOf(ingreso.getFrecuenciaArterial().getSistolica()));
        ingresoDTO.setFrecuenciaDiastolica(String.valueOf(ingreso.getFrecuenciaArterial().getDiastolica()));

        return ingresoDTO;
    }
}
