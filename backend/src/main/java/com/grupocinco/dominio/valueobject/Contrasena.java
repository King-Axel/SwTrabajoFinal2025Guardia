package com.grupocinco.dominio.valueobject;

public class Contrasena {
    private final String contrasenaHasheada;

    private Contrasena(String contrasenaHasheada) {
        if (contrasenaHasheada == null || contrasenaHasheada.isBlank()) throw new IllegalArgumentException("El hash no puede estar vacío");
        this.contrasenaHasheada = contrasenaHasheada;
    }

    public static void validarRaw(String contrasenaRaw) {
        if (contrasenaRaw == null || contrasenaRaw.isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria");
        if (contrasenaRaw.length() < 8) throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
    }

    public static Contrasena of(String contrasenaHasheada) {
        return new Contrasena(contrasenaHasheada);
    }

    public String get() {
        return contrasenaHasheada;
    }
}
