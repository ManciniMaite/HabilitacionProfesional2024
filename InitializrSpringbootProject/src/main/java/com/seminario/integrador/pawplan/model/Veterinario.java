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
public class Veterinario {
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNac;
    private String cuil;
    private int matricula;
    private boolean esIndependiente;
    private boolean haceGuardia;
    private ArrayList<DiaHorarioAtencion> horario;
    private ArrayList<Especialidad> especialidad;
    private boolean heceDomicilio;
}
