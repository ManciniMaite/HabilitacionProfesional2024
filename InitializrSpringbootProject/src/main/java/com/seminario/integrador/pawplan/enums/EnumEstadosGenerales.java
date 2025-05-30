package com.seminario.integrador.pawplan.enums;

public enum EnumEstadosGenerales implements EnumErroresInterface {
	
	OK(200,"OK","REQUEST PROCESADO OK"),
	ERROR(10000,"ERROR","ERROR"),
	ERROR_10001(10001,"ERROR FALTA PARAMETRO VETERINARIO/VETERINARIA","ERROR"),
	ERROR_10002(10002,"ERROR AL REGISTRAR TURNO","ERROR");

	private final int codigo;
    private final String mensaje;
    private final String estado;
    
	private EnumEstadosGenerales(int codigo, String mensaje, String estado) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.estado = estado;
	}

	public int getCodigo() {
        return codigo;
    }
    public String getMensaje() {
        return mensaje;
    }

	public String getEstado() {
		return estado;
	}
	
	

}
