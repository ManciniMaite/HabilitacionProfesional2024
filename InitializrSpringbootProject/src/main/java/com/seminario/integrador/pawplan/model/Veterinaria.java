/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;

import java.util.ArrayList;

/**
 *
 * @author maite
 */
public class Veterinaria {
    private String razonSocial;
    private String cuit;
    private boolean haceGuardia;
    private boolean aptoCirugia;
    private ArrayList<DiaHorarioAtencion> horarioAtencion;
    private ArrayList<Veterinario> veterinarios;
    private boolean heceDomicilio;
}
