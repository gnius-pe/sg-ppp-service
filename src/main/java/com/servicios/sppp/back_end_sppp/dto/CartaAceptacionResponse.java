package com.servicios.sppp.back_end_sppp.dto;

import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;

public class CartaAceptacionResponse {
    private long id;
    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private String url;
    private EstadoCartaAceptacion estado;
    private AlumnoResponse alumno;
    private boolean isDeleted;

    public CartaAceptacionResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EstadoCartaAceptacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoCartaAceptacion estado) {
        this.estado = estado;
    }

    public AlumnoResponse getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoResponse alumno) {
        this.alumno = alumno;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}