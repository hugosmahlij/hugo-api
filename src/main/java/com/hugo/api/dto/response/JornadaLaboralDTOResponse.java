package com.hugo.api.dto.response;

import java.time.LocalDate;

public class JornadaLaboralDTOResponse {

    private Integer id;
    private Integer nroDocumento;
    private String nombreCompleto;
    private LocalDate fecha;
    private String concepto;
    private Integer horasTrabajadas;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
}
