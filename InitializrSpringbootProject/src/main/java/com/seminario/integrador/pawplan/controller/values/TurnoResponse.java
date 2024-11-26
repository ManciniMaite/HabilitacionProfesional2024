package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;

import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.Turno;

public class TurnoResponse extends Response {

	private ArrayList<Horario> horariosDisponibles;
	private Turno turno;
	private Estado estadoReserva;
	
	public ArrayList<Horario> getHorariosDisponibles() {
		return horariosDisponibles;
	}

	public void setHorariosDisponibles(ArrayList<Horario> horariosDisponibles) {
		this.horariosDisponibles = horariosDisponibles;
	}

	public Estado getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(Estado estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	
	
}
