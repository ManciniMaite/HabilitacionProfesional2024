package com.seminario.integrador.pawplan.controller.values;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FiltroTurnoRq {
    @JsonProperty("idAnimal")
    private Long idAnimal;
    @JsonProperty("idCliente")
    private Long idCliente; 
    @JsonProperty("idVeterinaria")
    private Long idVeterinaria;
    @JsonProperty("idVeterinario")
    private Long idVeterinario;
    @JsonProperty("nEstado")
    private String nEstado;
    @JsonProperty("fecha")
    private Date fecha;

    @JsonProperty("page")
    private int page;
    @JsonProperty("size")
    private int size;
    @JsonProperty("orderDir")
    private String orderDir;
    @JsonProperty("orderBy")
    private String orderBy;
}
