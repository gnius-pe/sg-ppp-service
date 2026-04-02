package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;

@Entity
@Table(name = "revision_pre_documentos_ppp")
public class RevisionPreDocumentoPPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @OneToOne
    @JoinColumn(name = "id_pre_documentos_ppp")
    private PreDocumentosPPP preDocumentosPPP;

    @Column(name = "correcciones")
    private String correcciones;

    public RevisionPreDocumentoPPP() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public PreDocumentosPPP getPreDocumentosPPP() {
        return preDocumentosPPP;
    }

    public void setPreDocumentosPPP(PreDocumentosPPP preDocumentosPPP) {
        this.preDocumentosPPP = preDocumentosPPP;
    }

    public String getCorrecciones() {
        return correcciones;
    }

    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }
}
