package com.seminario.integrador.pawplan.enums;

public enum EnumEstadosGenerales implements EnumErroresInterface {
	
	OK(200,"OK"),
	ERROR(10000,"ERROR"),
	ERROR_10001(10001,"ERROR FALTA PARAMETRO VETERINARIO/VETERINARIA");

	private final int codigo;
    private final String mensaje;

    
	private EnumEstadosGenerales(int codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public int getCodigo() {
        return codigo;
    }
    public String getMensaje() {
        return mensaje;
    }
	
	

}
