package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;

@Entity
@Table(name = "pre_documentos_ppp")
public class PreDocumentosPPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    @OneToOne
    @JoinColumn(name = "id_solicitud")
    private SolicitudPPP solicitud;

    @OneToOne
    @JoinColumn(name = "id_carta_aceptacion")
    private CartaACeptacion cartaAceptacion;

    @OneToOne
    @JoinColumn(name = "id_formato_f1")
    private FormatoF1 formatoF1;

    @OneToOne
    @JoinColumn(name = "id_revision_pre_documento")
    private RevisionPreDocumentoPPP revisionPreDocumento;

    @Column(name = "estado_documentos_ppp")
    private boolean estadoDocumentoPPP;

    public PreDocumentosPPP() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public SolicitudPPP getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudPPP solicitud) {
        this.solicitud = solicitud;
    }

    public CartaACeptacion getCartaAceptacion() {
        return cartaAceptacion;
    }

    public void setCartaAceptacion(CartaACeptacion cartaAceptacion) {
        this.cartaAceptacion = cartaAceptacion;
    }

    public FormatoF1 getFormatoF1() {
        return formatoF1;
    }

    public void setFormatoF1(FormatoF1 formatoF1) {
        this.formatoF1 = formatoF1;
    }

    public RevisionPreDocumentoPPP getRevisionPreDocumento() {
        return revisionPreDocumento;
    }

    public void setRevisionPreDocumento(RevisionPreDocumentoPPP revisionPreDocumento) {
        this.revisionPreDocumento = revisionPreDocumento;
    }

    public boolean isEstadoDocumentoPPP() {
        return estadoDocumentoPPP;
    }

    public void setEstadoDocumentoPPP(boolean estadoDocumentoPPP) {
        this.estadoDocumentoPPP = estadoDocumentoPPP;
    }
}
