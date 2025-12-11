package com.grupocinco.dominio.exceptions;

public class PersonalInexistenteException extends RuntimeException {
    public PersonalInexistenteException(String message) {
        super(message);
    }
}
