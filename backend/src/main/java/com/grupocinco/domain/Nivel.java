package com.grupocinco.domain;

import lombok.Getter;

import java.time.Duration;

public class Nivel {
    private Integer nivel;
    @Getter
    private String nombre;
    private Duration duracionMaxEspera;

    public Nivel(Integer nivel, String nombre, Duration duracionMaxEspera) {
        this.nivel = nivel;
        this.nombre = nombre;
        this.duracionMaxEspera = duracionMaxEspera;
    }
}
