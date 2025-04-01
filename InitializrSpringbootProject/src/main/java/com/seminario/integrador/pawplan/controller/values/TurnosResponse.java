package com.seminario.integrador.pawplan.controller.values;

import java.util.List;

import com.seminario.integrador.pawplan.model.Turno;

public class TurnosResponse extends Response{
	
	private List<Turno> turnos;

	public List<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public TurnosResponse() {
	}	
	
	public TurnosResponse(List<Turno> turnos) {
		super();
		this.turnos = turnos;
	}
	
	

}
