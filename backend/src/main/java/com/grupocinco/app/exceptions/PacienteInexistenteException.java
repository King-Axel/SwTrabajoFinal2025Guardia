package com.grupocinco.app.exceptions;

public class PacienteInexistenteException extends RuntimeException {
    public PacienteInexistenteException(String message) {
        super(message);
    }
}
