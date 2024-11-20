package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;
import java.util.Date;

import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.DetalleTurno;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;

public class TurnoRequest {

	private Date fechaConsulta;

	private Date fechaReserva; 
	private ArrayList<DetalleTurno> detalleTurno;
	private Estado estado;
    private Long veterinariaId;
    private Long veterinarioId;
    private boolean esADomicilio;
    private String duracionEstimada;
    private String descripcion;
    private float monto;
    private boolean esGuardia;
	
	
	
	
	
	
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public ArrayList<DetalleTurno> getDetalleTurno() {
		return detalleTurno;
	}

	public void setDetalleTurno(ArrayList<DetalleTurno> detalleTurno) {
		this.detalleTurno = detalleTurno;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	

	public Long getVeterinariaId() {
		return veterinariaId;
	}

	public void setVeterinariaId(Long veterinariaId) {
		this.veterinariaId = veterinariaId;
	}

	public Long getVeterinarioId() {
		return veterinarioId;
	}

	public void setVeterinarioId(Long veterinarioId) {
		this.veterinarioId = veterinarioId;
	}

	public boolean isEsADomicilio() {
		return esADomicilio;
	}

	public void setEsADomicilio(boolean esADomicilio) {
		this.esADomicilio = esADomicilio;
	}

	public String getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(String duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public boolean isEsGuardia() {
		return esGuardia;
	}

	public void setEsGuardia(boolean esGuardia) {
		this.esGuardia = esGuardia;
	}
	
}
