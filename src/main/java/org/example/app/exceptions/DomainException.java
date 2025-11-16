package org.example.app.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}

