package com.seminario.integrador.pawplan.controller.values;

import java.util.List;

import org.springframework.data.domain.Page;

import com.seminario.integrador.pawplan.model.Turno;

import lombok.Data;

@Data
public class PaginaTurnosRs extends Response{
    private List<TurnoFb> turnos;
    private Long total;
}
