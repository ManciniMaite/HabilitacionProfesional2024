/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import javax.persistence.Entity;

/**
 *
 * @author sebastian
 */
@Entity
public class Usuario {
    
    
    private String nombreUsuario;
    private String contrasenia;
    private String telefono;
    private String correo;
    private Domicilio domicilio;
}
