package com.servicios.sppp.back_end_sppp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "Ejemplo de cuerpo de solicitud para crear un alumno")
public class AlumnoRequest {

    @ApiModelProperty(value = "Nombre del alumno", example = "igor")
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @ApiModelProperty(value = "Apellido del alumno", example = "ramos cruzado")
    @NotBlank(message = "El apellido es requerido")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;

    @ApiModelProperty(value = "Email del alumno", example = "igor.ramos@unas.edu.pe")
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @ApiModelProperty(value = "Código del alumno", example = "0020190504")
    @NotBlank(message = "El código es requerido")
    @Size(max = 20, message = "El código no puede exceder 20 caracteres")
    private String codigo;

    @ApiModelProperty(value = "Dirección actual", example = "Tingo Maria")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccionActual;

    @ApiModelProperty(value = "Número de celular", example = "929417416")
    @Size(max = 20, message = "El número de celular no puede exceder 20 caracteres")
    private String numeroCelular;

    @ApiModelProperty(value = "Contraseña", example = "12345678")
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

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
}