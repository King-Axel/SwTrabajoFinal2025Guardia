package org.example.domain;

import org.example.app.exceptions.CuilInvalidoException;
import org.example.app.exceptions.DomainException;

import java.util.Objects;

public abstract class Persona {
    private String cuil;
    private String apellido;
    private String nombre;

    public String getCuil() {
        return cuil;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona(String cuil, String apellido, String nombre) {
        assertApellidoNotEmpty(apellido);
        assertNombreNotEmpty(nombre);
        assertCuilNotEmpty(cuil);
        validarFormatoCuil(cuil);
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    private void assertNombreNotEmpty(String nombre) {
        if (Objects.isNull(nombre) || nombre.isBlank()) {
            throw new DomainException("El nombre del paciente no puede ser nulo ni estar en blanco");
        }
    }

    private void assertApellidoNotEmpty(String apellido) {
        if (Objects.isNull(apellido) || apellido.isBlank()) {
            throw new DomainException("El apellido del paciente no puede ser nulo ni estar en blanco");
        }
    }

    private void assertCuilNotEmpty(String cuil) {
        if (Objects.isNull(cuil) || cuil.isBlank()) {
            throw new DomainException("El cuil del paciente no puede ser nulo ni estar en blanco");
        }
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
