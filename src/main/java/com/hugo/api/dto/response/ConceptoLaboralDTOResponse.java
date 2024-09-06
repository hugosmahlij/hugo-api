package com.hugo.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoLaboralDTOResponse {

    private Integer id;
    private String nombre;
    private Integer hsMinimo;
    private Integer hsMaximo;
    private Boolean laborable;

    // Constructores, getters y setters

    public ConceptoLaboralDTOResponse() {}

    public ConceptoLaboralDTOResponse(Integer id, String nombre, Integer hsMinimo, Integer hsMaximo, Boolean laborable) {
        this.id = id;
        this.nombre = nombre;
        this.hsMinimo = hsMinimo;
        this.hsMaximo = hsMaximo;
        this.laborable = laborable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHsMinimo() {
        return hsMinimo;
    }

    public void setHsMinimo(Integer hsMinimo) {
        this.hsMinimo = hsMinimo;
    }

    public Integer getHsMaximo() {
        return hsMaximo;
    }

    public void setHsMaximo(Integer hsMaximo) {
        this.hsMaximo = hsMaximo;
    }

    public Boolean getLaborable() {
        return laborable;
    }

    public void setLaborable(Boolean laborable) {
        this.laborable = laborable;
    }
}
