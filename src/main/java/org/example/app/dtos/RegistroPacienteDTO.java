package org.example.app.dtos;

public class RegistroPacienteDTO {

    private String cuil;
    private String apellido;
    private String nombre;

    private String calle;
    private Integer numero;
    private String localidad;

    private Long idObraSocial;      // opcional
    private String numeroAfiliado;  // opcional (pero requerido si hay obra social)

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(Long idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }
}

