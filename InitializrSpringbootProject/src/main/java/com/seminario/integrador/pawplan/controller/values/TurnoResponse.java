package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;
import java.util.List;

import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.Turno;

import lombok.Data;

@Data
public class TurnoResponse extends Response {

	private ArrayList<Horario> horariosDisponibles;
	private Turno turno;
	private Estado estadoReserva;
	private List<Turno> turnos;
	
	
	
}
