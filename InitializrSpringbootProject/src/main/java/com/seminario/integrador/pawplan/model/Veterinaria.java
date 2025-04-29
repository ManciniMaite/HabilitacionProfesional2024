/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private boolean haceGuardia;
    private boolean aptoCirugia;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @NotNull(message = "debe especificar sus horarios")
    private List<DiaHorarioAtencion> horarioAtencion;
    private boolean haceDomicilio;


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

    public boolean isHaceGuardia() {
        return haceGuardia;
    }

    public void setHaceGuardia(boolean haceGuardia) {
        this.haceGuardia = haceGuardia;
    }

    public boolean isAptoCirugia() {
        return aptoCirugia;
    }

    public void setAptoCirugia(boolean aptoCirugia) {
        this.aptoCirugia = aptoCirugia;
    }

    public List<DiaHorarioAtencion> getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(List<DiaHorarioAtencion> horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public boolean isHaceDomicilio() {
        return haceDomicilio;
    }

    public void setHaceDomicilio(boolean heceDomicilio) {
        this.haceDomicilio = heceDomicilio;
    }
    
    
}
