/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @author maite
 */
@Entity
@Data
public class Veterinaria extends Usuario{
    @NotNull(message = "debe contener razon social del establecimiento")
    private String razonSocial;
    @NotNull(message = "debe contener cuit")
    private String cuit;
    private Boolean haceGuardia;
    private Boolean aptoCirugia;
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "debe especificar sus horarios")
    private List<DiaHorarioAtencion> horarioAtencion;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Veterinario> veterinarios;
    private Boolean haceDomicilio;


    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Boolean isHaceGuardia() {
        return haceGuardia;
    }

    public void setHaceGuardia(Boolean haceGuardia) {
        this.haceGuardia = haceGuardia;
    }

    public Boolean isAptoCirugia() {
        return aptoCirugia;
    }

    public void setAptoCirugia(Boolean aptoCirugia) {
        this.aptoCirugia = aptoCirugia;
    }

    public List<DiaHorarioAtencion> getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(List<DiaHorarioAtencion> horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    public Boolean isHaceDomicilio() {
        return haceDomicilio;
    }

    public void setHaceDomicilio(Boolean heceDomicilio) {
        this.haceDomicilio = heceDomicilio;
    }
    
    
}
