package com.seminario.integrador.pawplan.controller.values;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public interface TurnoFb {
    @Column(name = "id")
    Long getId();
    @Column(name = "id_animal")
    Long getIdAnimal();
    @Column(name = "nombre_animal")
    String getNombreAnimal();
    @Column(name = "id_veterinario")
    Long getIdVeterinario();
    @Column(name = "nombre_veterinario")
    String getNombreVeterinario();
    @Column(name = "id_veterinaria")
    Long getIdVeterinaria();
    @Column(name = "nombre_veterinaria")
    String getNombreVeterinaria();
    @Column(name = "especie")
    String getEspecie();
    @Column(name = "raza")
    String getRaza();
    @Column(name = "fecha")
    LocalDateTime getFecha();
}