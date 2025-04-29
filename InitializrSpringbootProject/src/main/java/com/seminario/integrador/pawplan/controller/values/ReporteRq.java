package com.seminario.integrador.pawplan.controller.values;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReporteRq {
    private Date fechaInicio;
    private Date fechaFin;
    private List<Long> idsEstados; 
}
