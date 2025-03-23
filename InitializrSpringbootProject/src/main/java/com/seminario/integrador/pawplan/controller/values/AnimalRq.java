package com.seminario.integrador.pawplan.controller.values;

import java.sql.Date;

import lombok.Data;

@Data
public class AnimalRq {
    private Long id;
    private String nombre;
    private Date fechaNac;
    private float peso;
    private String foto;
    private Long razaId; 
    private Long usuarioId;
}
