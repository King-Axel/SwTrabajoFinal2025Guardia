package org.example.domain;

import org.example.app.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    public void crearPaciente(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = "Hector";

        //Ejecucion
        Paciente paciente = new Paciente(cuil, apellido, nombre);

        //Validacion
        assertEquals(cuil, paciente.getCuil());
        assertEquals(apellido, paciente.getApellido());
        assertEquals(nombre, paciente.getNombre());
    }

    @Test
    public void crearPacienteConNombreNulo(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = null;

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
                                new Paciente(cuil, apellido, nombre);
                                });
        assertEquals("El nombre del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConNombreVacio(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = "";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
                                new Paciente(cuil, apellido, nombre);
                                });
        assertEquals("El nombre del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConNombreEspaciado(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = "     ";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
                                new Paciente(cuil, apellido, nombre);
                                });
        assertEquals("El nombre del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConNombreConCaracteresNumericos(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = "H3ctor";

        //Ejecucion
        Paciente paciente = new Paciente(cuil, apellido, nombre);

        //Validacion
        assertEquals(cuil, paciente.getCuil());
        assertEquals(apellido, paciente.getApellido());
        assertEquals(nombre, paciente.getNombre());
    }

    @Test
    public void crearPacienteConNombreConCaracteresEspeciales(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "Lopez";
        String nombre = "H'ector";

        //Ejecucion
        Paciente paciente = new Paciente(cuil, apellido, nombre);

        //Validacion
        assertEquals(cuil, paciente.getCuil());
        assertEquals(apellido, paciente.getApellido());
        assertEquals(nombre, paciente.getNombre());
    }

    @Test
    public void crearPacienteConApellidoNulo(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = null;
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El apellido del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConApellidoVacio(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "";
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El apellido del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConApellidoEspaciado(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "     ";
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El apellido del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConApellidoConCaracteresNumericos(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "L0p3z";
        String nombre = "Hector";

        //Ejecucion
        Paciente paciente = new Paciente(cuil, apellido, nombre);

        //Validacion
        assertEquals(cuil, paciente.getCuil());
        assertEquals(apellido, paciente.getApellido());
        assertEquals(nombre, paciente.getNombre());
    }

    @Test
    public void crearPacienteConApellidoConCaracteresEspeciales(){
        //Preparacion
        String cuil = "20-12345678-1";
        String apellido = "@López";
        String nombre = "Hector";

        //Ejecucion
        Paciente paciente = new Paciente(cuil, apellido, nombre);

        //Validacion
        assertEquals(cuil, paciente.getCuil());
        assertEquals(apellido, paciente.getApellido());
        assertEquals(nombre, paciente.getNombre());
    }

    @Test
    public void crearPacienteConCuilConFormatoInvalido(){
        //Preparacion
        String cuil = "20123456781";
        String apellido = "Lopez";
        String nombre = "Hector";

        //Ejecucion y validacion
        CuilInvalidoException thrown = assertThrows(CuilInvalidoException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("Cuil invalido (formato esperado: XX-XXXXXXXX-X)", thrown.getMessage());
    }

    @Test
    public void crearPacienteConCuilNulo(){
        //Preparacion
        String cuil = null;
        String apellido = "Lopez";
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El cuil del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConCuilVacio(){
        //Preparacion
        String cuil = "";
        String apellido = "Lopez";
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El cuil del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearPacienteConCuilEspaciado(){
        //Preparacion
        String cuil = "     ";
        String apellido = "Lopez";
        String nombre = "Hector";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new Paciente(cuil, apellido, nombre);
        });
        assertEquals("El cuil del paciente no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

}