package com.grupocinco.dominio.exceptions;

public class RolInvalidoException extends RuntimeException {
    public RolInvalidoException(String mensaje) {
        super(mensaje);
    }
}
