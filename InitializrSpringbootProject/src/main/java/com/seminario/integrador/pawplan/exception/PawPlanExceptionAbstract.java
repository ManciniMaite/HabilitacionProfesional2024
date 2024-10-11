/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.exception;

import com.seminario.integrador.pawplan.enums.EnumErroresInterface;

/**
 *
 * @author sebastian
 */
public class PawPlanExceptionAbstract extends Exception {

	private int codigo;
	
	private EnumErroresInterface enumError;
	
	public PawPlanExceptionAbstract() {
		super();
	}

	
	public PawPlanExceptionAbstract(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	
	public PawPlanExceptionAbstract(int codigo) {
		super( Integer.toString(codigo) );
		this.codigo = codigo;
	}
	
	
	public PawPlanExceptionAbstract(int codigo, String mensaje) {
		super( Integer.toString(codigo) + " - " + mensaje);
		this.codigo = codigo;
	}
	
	
	public PawPlanExceptionAbstract(int codigo, Throwable arg1) {
		super( Integer.toString(codigo), arg1 );
		this.codigo = codigo;
	}

	public PawPlanExceptionAbstract(EnumErroresInterface error) {
		super( error.getMensaje() );
		this.enumError = error;
		this.codigo = error.getCodigo();
	}
	
	public PawPlanExceptionAbstract(EnumErroresInterface error, String message) {
		super( error.getMensaje()  + " - " + message);
		this.enumError = error;
		this.codigo = error.getCodigo();
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public EnumErroresInterface getEnumError() {
		return this.enumError;
	}
	
}