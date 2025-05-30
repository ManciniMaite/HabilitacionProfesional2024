package com.seminario.integrador.pawplan.controller.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AtenderTurnoRq {
    @JsonProperty("idTurno")
    private Long idTurno;
    @JsonProperty("descripcionPublica")
    private String descripcionPublica;
    @JsonProperty("descripcionPrivada")
    private String descripcionPrivada;
}
