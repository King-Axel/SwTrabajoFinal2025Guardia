package com.grupocinco.app.infraestructura.mappers;

import com.grupocinco.app.infraestructura.dtos.IngresoDTO;
import com.grupocinco.dominio.Ingreso;

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
        ingresoDTO.setFrecuenciaSistolica(String.valueOf(ingreso.getTensionArterial().getSistolica()));
        ingresoDTO.setFrecuenciaDiastolica(String.valueOf(ingreso.getTensionArterial().getDiastolica()));
        ingresoDTO.setFechaIngreso(ingreso.getFechaIngreso());

        return ingresoDTO;
    }
}
