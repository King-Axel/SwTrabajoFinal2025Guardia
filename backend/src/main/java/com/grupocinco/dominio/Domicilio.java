package com.grupocinco.dominio;

import com.grupocinco.dominio.exceptions.DatoMandatorioOmitidoException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Domicilio {
    private String calle;
    private Integer numero;
    private String localidad;

    public Domicilio(String calle, Integer numero, String localidad){
        if(calle == null || calle.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Calle");
        if(localidad == null || localidad.isBlank()) throw new DatoMandatorioOmitidoException("Falta el dato Localidad");
        if(numero == null) throw new DatoMandatorioOmitidoException("Falta el dato Numero de Calle");

        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
    }
}
