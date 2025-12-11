package com.grupocinco.dominio.exceptions;

public class PacienteInexistenteException extends RuntimeException {
    public PacienteInexistenteException(String message) {
        super(message);
    }
}
