package com.grupocinco.app.dtos;

import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Medico;
import com.grupocinco.domain.Persona;

public class PersonaDTO {
    private String rol;
    private String nombre;
    private String apellido;
    private String matricula;

    public PersonaDTO() {}

    public PersonaDTO(String apellido, String nombre, String matricula, String rol) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.matricula = matricula;
        this.rol = rol;
    }

    public static PersonaDTO aDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setApellido(persona.getApellido());
        dto.setNombre(persona.getNombre());

        if(persona instanceof Medico) {
            Medico medico = (Medico) persona;
            dto.setMatricula(medico.getMatricula());
        }

        dto.setRol(Rol.desdeString(persona.getClass().getSimpleName()).name());

        return dto;
    }

    public static Persona aClase(PersonaDTO dto) {
        Rol rol1 = Rol.desdeString(dto.getRol());

        if (rol1 == Rol.MEDICO) {
            return new Medico(dto.getApellido(), dto.getNombre(), dto.getMatricula());
        } else {
            return new Enfermera(dto.getApellido(), dto.getNombre());
        }
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
