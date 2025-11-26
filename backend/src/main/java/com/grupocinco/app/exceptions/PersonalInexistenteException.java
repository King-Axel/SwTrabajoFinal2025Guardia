package com.grupocinco.app.exceptions;

public class PersonalInexistenteException extends RuntimeException {
    public PersonalInexistenteException(String message) {
        super(message);
    }
}
