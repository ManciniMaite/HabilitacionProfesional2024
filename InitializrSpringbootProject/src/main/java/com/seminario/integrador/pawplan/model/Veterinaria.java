/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;

/**
 *
 * @author maite
 */
@Entity
public class Veterinaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private String cuit;
    private boolean haceGuardia;
    private boolean aptoCirugia;
    @OneToMany
    private ArrayList<DiaHorarioAtencion> horarioAtencion;
    @OneToMany
    private ArrayList<Veterinario> veterinarios;
    private boolean heceDomicilio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ArrayList<DiaHorarioAtencion> getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(ArrayList<DiaHorarioAtencion> horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public ArrayList<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(ArrayList<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    public boolean isHeceDomicilio() {
        return heceDomicilio;
    }

    public void setHeceDomicilio(boolean heceDomicilio) {
        this.heceDomicilio = heceDomicilio;
    }
    
    
}
