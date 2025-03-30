package com.seminario.integrador.pawplan.enums;

public enum EnumEstados {

	CANCELADO("CANCELADO"),
	RESERVADO("RESERVADO"),
	ATENDIDO("ATENDIDO");

    private final String nombre;
    
	private EnumEstados(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
