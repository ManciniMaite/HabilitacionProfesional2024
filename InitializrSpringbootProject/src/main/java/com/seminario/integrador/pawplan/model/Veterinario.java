/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * @author maite
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Veterinario extends Usuario {
    
    @NotNull(message = "debe contener nombre")
    private String nombre;
    @NotNull(message = "debe contener apellido")
    private String apellido;
    @NotNull(message = "debe contener dni")
    private String dni;
    private String fechaNac;
    @NotNull(message = "debe contener matricula")
    private int matricula;
    private Boolean esIndependiente;
    private Boolean haceGuardia;
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private List<DiaHorarioAtencion> horarios;  
    private Boolean haceDomicilio;

    @ManyToMany
    @JoinTable(
        name = "veterinario_tipo_especie",
        joinColumns = @JoinColumn(name = "veterinario_id"),
        inverseJoinColumns = @JoinColumn(name = "tipo_especie_id")
    )
    @NotNull(message = "debe especificar el/los tipo(s) de especie que atiende")
    private List<TipoEspecie> tiposEspecie;


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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Boolean isEsIndependiente() {
        return esIndependiente;
    }

    public void setEsIndependiente(Boolean esIndependiente) {
        this.esIndependiente = esIndependiente;
    }

    public Boolean isHaceGuardia() {
        return haceGuardia;
    }

    public void setHaceGuardia(Boolean haceGuardia) {
        this.haceGuardia = haceGuardia;
    }

    public Boolean isHaceDomicilio() {
        return haceDomicilio;
    }

    public void setHaceDomicilio(Boolean heceDomicilio) {
        this.haceDomicilio = heceDomicilio;
    }
    
    
}
