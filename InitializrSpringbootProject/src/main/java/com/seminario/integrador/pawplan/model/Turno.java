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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author maite
 */
@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaHoraReserva; 
    private Date fecha; 
    private Date hora; 
    
    @ManyToOne(cascade = CascadeType.ALL)
    private ArrayList<DetalleTurno> detalleTurno;
    @OneToMany(cascade = CascadeType.ALL)
    private Estado estado;
    @OneToMany(cascade = CascadeType.ALL)
    private Cliente cliente;
    private Veterinaria veterinaria;
    private Veterinario veterinario;
    private boolean esADomicilio;
    private String duracionEstimada;
    private String descripcion;
    private float monto;
    private boolean esGuardia;
}
