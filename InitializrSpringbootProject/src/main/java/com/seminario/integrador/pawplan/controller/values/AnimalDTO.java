package com.seminario.integrador.pawplan.controller.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AnimalDTO {
    @JsonProperty("animal_id")
    private Long animalId;
    @JsonProperty("animal_nombre")
    private String animalNombre;
    @JsonProperty("raza_nombre")
    private String razaNombre;
    @JsonProperty("especie_nombre")
    private String especieNombre;
}
