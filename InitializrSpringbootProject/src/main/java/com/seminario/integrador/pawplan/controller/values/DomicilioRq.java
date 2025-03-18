package com.seminario.integrador.pawplan.controller.values;

import lombok.Data;


@Data
public class DomicilioRq {
    private Long idDomicilio;
    private Long ciudadId;
    private String calle;
    private String numero;
    private String observaciones;
    private String usuario; //cuit o dni
}
