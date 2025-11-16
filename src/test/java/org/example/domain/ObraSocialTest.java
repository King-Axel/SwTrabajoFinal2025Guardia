package org.example.domain;

import org.example.app.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ObraSocialTest {

    @Test
    public void crearObraSocial(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = "Boreal";

        //Ejecucion
        ObraSocial obraSocial = new ObraSocial(id,nombre);

        //Validacion
        assertEquals(id, obraSocial.getId());
        assertEquals(nombre, obraSocial.getNombre());
    }

    @Test
    public void crearObraSocialConNombreNulo(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = null;

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
                                    new ObraSocial(id,nombre);
                                });
        assertEquals("El nombre de la obra social no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearObraSocialConNombreVacio(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = "";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new ObraSocial(id,nombre);
        });
        assertEquals("El nombre de la obra social no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearObraSocialConNombreEspaciado(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = "    ";

        //Ejecucion y validacion
        DomainException thrown = assertThrows(DomainException.class, () -> {
            new ObraSocial(id,nombre);
        });
        assertEquals("El nombre de la obra social no puede ser nulo ni estar en blanco", thrown.getMessage());
    }

    @Test
    public void crearObraSocialConNombreConCaracteresNumericos(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = "B0r3al";

        //Ejecucion
        ObraSocial obraSocial = new ObraSocial(id,nombre);

        //Validacion
        assertEquals(id, obraSocial.getId());
        assertEquals(nombre, obraSocial.getNombre());
    }

    @Test
    public void crearObraSocialConNombreConCaracteresEspeciales(){
        //Preparacion
        UUID id = UUID.randomUUID();
        String nombre = "B'oreal";

        //Ejecucion
        ObraSocial obraSocial = new ObraSocial(id,nombre);

        //Validacion
        assertEquals(id, obraSocial.getId());
        assertEquals(nombre, obraSocial.getNombre());
    }

}