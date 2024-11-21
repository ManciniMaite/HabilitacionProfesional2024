/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maite
 */
@Entity
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
    private boolean esIndependiente;
    private boolean haceGuardia;
    @OneToMany(cascade = CascadeType.ALL)
    private List<DiaHorarioAtencion> horario;
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "debe especificar su/s especialidad/es")
    private List<Especialidad> especialidad;
    private boolean haceDomicilio;


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

    public boolean isEsIndependiente() {
        return esIndependiente;
    }

    public void setEsIndependiente(boolean esIndependiente) {
        this.esIndependiente = esIndependiente;
    }

    public boolean isHaceGuardia() {
        return haceGuardia;
    }

    public void setHaceGuardia(boolean haceGuardia) {
        this.haceGuardia = haceGuardia;
    }

    public List<DiaHorarioAtencion> getHorario() {
        return horario;
    }

    public void setHorario(List<DiaHorarioAtencion> horario) {
        this.horario = horario;
    }

    public List<Especialidad> getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(List<Especialidad> especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isHaceDomicilio() {
        return haceDomicilio;
    }

    public void setHaceDomicilio(boolean heceDomicilio) {
        this.haceDomicilio = heceDomicilio;
    }
    
    
}
