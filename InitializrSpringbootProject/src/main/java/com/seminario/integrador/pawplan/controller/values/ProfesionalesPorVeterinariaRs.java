package com.seminario.integrador.pawplan.controller.values;

import java.util.List;

import lombok.Data;

@Data
public class ProfesionalesPorVeterinariaRs extends Response {
    private List<ProfesionalPorVeterinaria> profesionales;
}
