package com.seminario.integrador.pawplan.controller.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AtenderTurnoRq {
    @JsonProperty("idTurno")
    private Long idTurno;
    @JsonProperty("descripcion")
    private String descripcion;
}
