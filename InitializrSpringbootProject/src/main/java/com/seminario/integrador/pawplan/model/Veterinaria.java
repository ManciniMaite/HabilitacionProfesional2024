/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private ArrayList<DiaHorarioAtencion> horarioAtencion;
    private ArrayList<Veterinario> veterinarios;
    private boolean heceDomicilio;
}
