package com.seminario.integrador.pawplan.controller.values;

public class UsuarioResponse<T> extends Response {

	private T usuario;

	public T getUsuario() {
		return usuario;
	}

	public void setUsuario(T usuario) {
		this.usuario = usuario;
	}
	
	
}
