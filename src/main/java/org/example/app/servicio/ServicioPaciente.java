package org.example.app.servicio;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Domicilio;
import org.example.domain.ObraSocial;

public class ServicioPaciente {
    private RepositorioPacientes dbPacientes;

    public  ServicioPaciente(RespositorioPacientes dbPacientes){
        this.dbPacientes = repositorioPacientes;
    }
    public void ingresarPaciente(String cuil, String apellido, String nombre, Domicilio domicilio, ObraSocial obraSocial){

    }
}
