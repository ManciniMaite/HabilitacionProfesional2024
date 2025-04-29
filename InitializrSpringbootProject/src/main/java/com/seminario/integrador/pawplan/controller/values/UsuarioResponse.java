package com.seminario.integrador.pawplan.controller.values;

import lombok.Data;

@Data
public class UsuarioResponse<T> extends Response {

	private T usuario;
	
	private String pregunta;

	public T getUsuario() {
		return usuario;
	}

	public void setUsuario(T usuario) {
		this.usuario = usuario;
	}
	
	
}
