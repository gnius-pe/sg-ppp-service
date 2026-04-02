package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "solicitud_ppp")
public class SolicitudPPP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    private String asunto;

    private String url;

    @Column(name = "estado_solicitud_ppp")
    boolean estadoSolicitudPPP;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    public SolicitudPPP() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public boolean isEstadoSolicitudPPP() {
        return estadoSolicitudPPP;
    }

    public void setEstadoSolicitudPPP(boolean estadoSolicitudPPP) {
        this.estadoSolicitudPPP = estadoSolicitudPPP;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
