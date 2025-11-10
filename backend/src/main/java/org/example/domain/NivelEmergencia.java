package org.example.domain;

public enum NivelEmergencia {
    CRITICA("Critica", 1),
    EMERGENCIA("Emergencia", 2),
    URGENCIA("Urgencia", 3),
    URGENCIA_MENOR("Urgencia menor", 4),
    SIN_URGENCIA("Sin Urgencia", 5),;

    final String nombre;
    int prioridad;

    NivelEmergencia(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public boolean esValido(String nombre) {
        if (nombre == null) return false;
        return nombre.equals(this.nombre);
    }

    public int getPrioridad() {
        return prioridad;
    }
}
