package org.example.domain;

public class Paciente {
    private String cuil;
    private String apellido;
    private String nombre;
    private Domicilio domicilio;
    private Afiliado afiliacion;

    public Paciente(String cuil, String apellido, String nombre) {
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public Paciente(String cuil, String apellido, String nombre, Domicilio domicilio, Afiliado afiliacion ) {
        this.cuil = cuil;
        this.apellido = apellido;
        this.nombre = nombre;
        this.afiliacion = afiliacion;
        this.domicilio = domicilio;
    }

    public String getApellido() { return this.apellido; }

    public String getNombre() { return this.nombre; }

    public Domicilio getDomicilio() { return this.domicilio; }

    public Afiliado getAfiliacion() { return this.afiliacion; }

    public String getCuil() {
        return this.cuil;
    }
}
