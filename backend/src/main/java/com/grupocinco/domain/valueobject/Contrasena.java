package com.grupocinco.domain.valueobject;

import com.grupocinco.app.security.PasswordHasher;

// tratar de desacoplar del PasswordHasher
public class Contrasena {
    private final String contrasenaHasheada;

    private Contrasena(String contrasenaHasheada) {
        this.contrasenaHasheada = contrasenaHasheada;
    }

    public static Contrasena fromRaw(String rawContrasena) {
        if (rawContrasena == null || rawContrasena.isBlank())
            throw new IllegalArgumentException("La contrasena es obligatoria");
        if (rawContrasena.length() < 8)
            throw new IllegalArgumentException("La contrasena debe tener al menos 8 caracteres");

        String hasheada = PasswordHasher.hash(rawContrasena);

        return new Contrasena(hasheada);
    }

    /*
    * Para reconstruir una cuenta desde DB, que tiene la contrasena como un String hasheado,
    * se requerira crear un ValueObject Contrasena, pero desde un String ya hasheado
     */
    public static Contrasena fromHashed(String hashedContrasena) {
        if  (hashedContrasena == null || hashedContrasena.isBlank())
            throw new IllegalArgumentException("La contrasena ingresada no es valida");
        return new Contrasena(hashedContrasena);
    }

    public String get() {
        return contrasenaHasheada;
    }
}
