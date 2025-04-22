package com.seminario.integrador.pawplan.controller.values;

import java.sql.Date;

import lombok.Data;

@Data
public class ReservarTurnoRq {
    private Long veterinariaId;
    private Long veterinarioId;
    private boolean esDomicilio;
    private long animalId;
    private String fecha;
    private String hora;
}
