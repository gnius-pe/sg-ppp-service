package com.servicios.sppp.back_end_sppp.modelos;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El código es requerido")
    @Size(max = 20, message = "El código no puede exceder 20 caracteres")
    @Column(unique = true)
    private String codigo;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccionActual;

    @Size(max = 20, message = "El número de celular no puede exceder 20 caracteres")
    private String numeroCelular;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "alumno")
    private List<SolicitudPPP> solicitudes;

    @OneToMany(mappedBy = "alumno")
    private List<CartaACeptacion> cartasAceptacion;

    @OneToMany(mappedBy = "alumno")
    private List<FormatoF1> formatosF1;

    @OneToMany(mappedBy = "alumno")
    private List<PreDocumentosPPP> preDocumentosPPP;

    public Alumno() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<SolicitudPPP> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudPPP> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<CartaACeptacion> getCartasAceptacion() {
        return cartasAceptacion;
    }

    public void setCartasAceptacion(List<CartaACeptacion> cartasAceptacion) {
        this.cartasAceptacion = cartasAceptacion;
    }

    public List<FormatoF1> getFormatosF1() {
        return formatosF1;
    }

    public void setFormatosF1(List<FormatoF1> formatosF1) {
        this.formatosF1 = formatosF1;
    }

    public List<PreDocumentosPPP> getPreDocumentosPPP() {
        return preDocumentosPPP;
    }

    public void setPreDocumentosPPP(List<PreDocumentosPPP> preDocumentosPPP) {
        this.preDocumentosPPP = preDocumentosPPP;
    }
}