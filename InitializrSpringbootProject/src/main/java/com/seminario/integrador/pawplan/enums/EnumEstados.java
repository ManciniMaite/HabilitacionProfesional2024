package com.seminario.integrador.pawplan.enums;

public enum EnumEstados {

	RESERVADO("RESERVADO"),
	CANCELADO("CANCELADO"),
	ACEPTADO("ACEPTADO"),
	RECHAZADO("RECHAZADO"),
	ATENDIDO("ATENDIDO");

    private final String nombre;
    
	private EnumEstados(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
