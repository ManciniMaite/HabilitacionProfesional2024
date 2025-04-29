package com.seminario.integrador.pawplan.controller.values;

import lombok.Data;

@Data
public class Contactos {

	private String nombre;
	private String domicilio;
	private String telefono;
    
	public Contactos(String nombre, String domicilio, String telefono) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }
}