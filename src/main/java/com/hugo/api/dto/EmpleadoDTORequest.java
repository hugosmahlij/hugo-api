package com.hugo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class EmpleadoDTORequest {

    @NotNull(message = "El nro de documento es obligatorio.")
    private Integer nroDocumento;

    @NotEmpty(message = "El nombre es obligatorio.")
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio.")
    private String apellido;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "La fecha de ingreso es obligatoria.")
    private LocalDate fechaIngreso;



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

    public @NotNull LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(@NotNull LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
