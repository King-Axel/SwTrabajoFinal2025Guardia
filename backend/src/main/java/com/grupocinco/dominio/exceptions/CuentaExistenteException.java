package com.grupocinco.dominio.exceptions;

public class CuentaExistenteException extends RuntimeException {
    public CuentaExistenteException() {
        super("Ya existe una cuenta con el email ingresado");
    }
}
