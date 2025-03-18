/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author maite
 */
@Entity
@Data
public class Cliente extends Usuario{
    @NotNull(message = "debe contener nombre")
    private String nombre;
    @NotNull(message = "debe contener apellido")
    private String apellido;
    @NotNull(message = "debe contener dni")
    private String dni;

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

}
