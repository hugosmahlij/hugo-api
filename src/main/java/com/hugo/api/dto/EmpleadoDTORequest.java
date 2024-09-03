package com.hugo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


public class EmpleadoDTORequest {

    @NotNull
    private Integer nroDocumento;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Date fechaNacimiento;

    @NotNull
    private Date fechaIngreso;

    public @NotNull Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(@NotNull Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public @NotBlank String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank String apellido) {
        this.apellido = apellido;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotNull Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public @NotNull Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(@NotNull Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
