/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author maite
 */
@Entity
@Data
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaHoraReserva; 
    private Date fechaHora; 
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado_id")
    private Estado estado;
    @ManyToOne()
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinaria_id")
    private Veterinaria veterinaria;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;
    private boolean esADomicilio;
    private String descripcionPublica;
    private String descripcionPrivada;
    
    
    
}
