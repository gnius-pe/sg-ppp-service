package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "formato_f1")
public class FormatoF1 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descripcion;

    @Column(name = "fecha_entrega")
    private String fechaEntrega;

    private String url;

    @Column(name = "estado_formato_f1")
    private boolean estadoFormatoF1;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    public FormatoF1() {
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

    public boolean isEstadoFormatoF1() {
        return estadoFormatoF1;
    }

    public void setEstadoFormatoF1(boolean estadoFormatoF1) {
        this.estadoFormatoF1 = estadoFormatoF1;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
