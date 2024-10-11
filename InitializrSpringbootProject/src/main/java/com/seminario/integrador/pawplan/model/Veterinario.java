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
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNac;
    private String cuil;
    private int matricula;
    private boolean esIndependiente;
    private boolean haceGuardia;
    @OneToMany
    private ArrayList<DiaHorarioAtencion> horario;
    @OneToMany
    private ArrayList<Especialidad> especialidad;
    private boolean heceDomicilio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
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

    public ArrayList<DiaHorarioAtencion> getHorario() {
        return horario;
    }

    public void setHorario(ArrayList<DiaHorarioAtencion> horario) {
        this.horario = horario;
    }

    public ArrayList<Especialidad> getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(ArrayList<Especialidad> especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isHeceDomicilio() {
        return heceDomicilio;
    }

    public void setHeceDomicilio(boolean heceDomicilio) {
        this.heceDomicilio = heceDomicilio;
    }
    
    
}
