package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "carta_aceptacion")
public class CartaACeptacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descripcion;

    @Column(name = "fecha_entrega")
    private String fechaEntrega;

    private String url;

    @Column(name = "estado_carta_aceptacion")
    private boolean estadoCartaAceptacion;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    public CartaACeptacion() {
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

    public boolean isEstadoCartaAceptacion() {
        return estadoCartaAceptacion;
    }

    public void setEstadoCartaAceptacion(boolean estadoCartaAceptacion) {
        this.estadoCartaAceptacion = estadoCartaAceptacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
