package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String codigo;
    private String direccionActual;
    private String numeroCelular;
    private String pasword;

    private String rol;

    @OneToMany(mappedBy = "docente")
    private List<RevisionPreDocumentoPPP> revisiones;

    public Docente(){

    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccionActual() {
        return direccionActual;
    }

    public void setDireccionActual(String direccionActual) {
        this.direccionActual = direccionActual;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public List<RevisionPreDocumentoPPP> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<RevisionPreDocumentoPPP> revisiones) {
        this.revisiones = revisiones;
    }
}
