package com.grupocinco.dominio.valueobject;


import java.util.regex.Pattern;

public class Email {
    private final String email;

    private Email(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email es obligatorio");

        Pattern PATRON_EMAIL = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
                Pattern.CASE_INSENSITIVE
        );

        if (!PATRON_EMAIL.matcher(email).matches())
            throw new IllegalArgumentException("El email ingresado no es valido");
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String get() {
        return email;
    }
}
