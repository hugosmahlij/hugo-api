package com.hugo.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class JornadaLaboralDTORequest {

    @NotNull(message = "'idEmpleado' es obligatorio.")
    private Long idEmpleado;

    @NotNull(message = "'idConcepto' es obligatorio.")
    private Integer idConcepto;

    @NotNull(message = "'fecha' es obligatorio.")
    private LocalDate fecha;

    private Integer horasTrabajadas;



    public @NotNull(message = "'idEmpleado' es obligatorio.") Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(@NotNull(message = "'idEmpleado' es obligatorio.") Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public @NotNull(message = "'idConcepto' es obligatorio.") Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(@NotNull(message = "'idConcepto' es obligatorio.") Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public @NotNull(message = "'fecha' es obligatorio.") LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NotNull(message = "'fecha' es obligatorio.") LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
}
