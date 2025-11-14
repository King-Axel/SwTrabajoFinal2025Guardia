package com.grupocinco.app.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordHasher {
    /* EXPLICACION
    * El constructor del Encoder de Argon2 recibe 5 parametros, estos son:
    * - salLenght: 16 Bytes
    * - hashLenght: 32 Bytes
    * - parallelism: 1 (hilos usados)
    * - memory: 4096KB
    * - iterations: 3
    * |Los valores colocados para instanciar fueron recomendados por ChatGPT|
     */

    private static final Argon2PasswordEncoder encoder =
            new Argon2PasswordEncoder(16, 32, 1, 4096, 3);

    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
