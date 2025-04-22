package com.seminario.integrador.pawplan.controller.values;

import java.sql.Date;

import lombok.Data;

@Data
public class DisponibilidadRq {
    private Date fecha;
    private Long veterinarioId;
}
