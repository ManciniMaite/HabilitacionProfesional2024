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
public class Turno {
    private String fechaHoraReserva; //DATETIME
    private String fecha; //Date
    private String hora; //Time
    private ArrayList<DetalleTurno> detalleTurno;
    private Estado estado;
    private Cliente cliente;
    private Veterinaria veterinaria;
    private Veterinario veterinario;
    private boolean esADomicilio;
    private String duracionEstimada;
    private String descripcion;
    private float monto;
    private boolean esGuardia;
}
