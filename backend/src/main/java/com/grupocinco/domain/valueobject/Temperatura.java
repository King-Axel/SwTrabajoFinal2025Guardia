package com.grupocinco.domain.valueobject;

public class Temperatura {
    private final Float temperatura;

    private Temperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public static Temperatura of(String temperatura) {
        return new Temperatura(validar(temperatura));
    }

    private static float validar(String valor) {
        float valorParseado;

        if (valor == null || valor.isBlank()) throw new IllegalArgumentException("Falta el dato Temperatura");

        try {
            valorParseado = Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La temperatura debe ser un numero");
        }

        if (valorParseado < 0) throw new IllegalArgumentException("La temperatura no puede ser negativa");

        return valorParseado;
    }

    public Float get() {
        return temperatura;
    }
}
