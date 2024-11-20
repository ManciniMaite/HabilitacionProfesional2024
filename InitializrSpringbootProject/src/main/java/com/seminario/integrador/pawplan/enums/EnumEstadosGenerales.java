package com.seminario.integrador.pawplan.enums;

public enum EnumEstadosGenerales implements EnumErroresInterface {
	
	OK(200,"OK"),
	ERROR(10000,"ERROR");

	private final int codigo;
    private final String mensaje;

    
	private EnumEstadosGenerales(int codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	@Override
	public int getCodigo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMensaje() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
