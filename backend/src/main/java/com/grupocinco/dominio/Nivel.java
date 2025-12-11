package com.grupocinco.dominio;

import lombok.Getter;

import java.time.Duration;

public class Nivel {
    private final Integer nivel;
    @Getter
    private final String nombre;
    private final Duration duracionMaxEspera;

    public Nivel(Integer nivel, String nombre, Duration duracionMaxEspera) {
        this.nivel = nivel;
        this.nombre = nombre;
        this.duracionMaxEspera = duracionMaxEspera;
    }
}
