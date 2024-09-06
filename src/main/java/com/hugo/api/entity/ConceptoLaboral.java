package com.hugo.api.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "concepto_laboral")
public class ConceptoLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(name = "hs_minimo")
    private Integer hsMinimo;

    @Column(name = "hs_maximo")
    private Integer hsMaximo;

    private Boolean laborable;

    public ConceptoLaboral(){

    }

    public ConceptoLaboral(Integer id, String nombre, Integer hsMinimo, Integer hsMaximo, Boolean laborable) {
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
