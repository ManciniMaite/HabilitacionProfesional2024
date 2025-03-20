package com.seminario.integrador.pawplan.controller.values;

import java.util.List;

import lombok.Data;

@Data
public class MatriculadosResponse {
    private String statussalida;
    private Long ctimestamp;
    private List<Matriculado> registros;
    private List<Object> camposseguros;
}
