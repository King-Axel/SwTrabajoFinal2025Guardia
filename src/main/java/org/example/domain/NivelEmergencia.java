package org.example.domain;

public enum NivelEmergencia {
    CRITICA("Critica"),
    EMERGENCIA("Emergencia"),
    URGENCIA("Urgencia"),
    URGENCIA_MENOR("Urgencia Menor"),
    SIN_URGENCIA("Sin Urgencia");

    String nombre;

    NivelEmergencia(String nombre) {
        this.nombre = nombre;
    }

    public boolean tieneNombre(String nombre) {
        return this.nombre.equals(nombre);
    }
}
