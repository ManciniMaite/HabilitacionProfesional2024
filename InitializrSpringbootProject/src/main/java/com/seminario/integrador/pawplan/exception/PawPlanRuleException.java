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
public class PawPlanRuleException extends PawPlanExceptionAbstract {

	public PawPlanRuleException() {
		super();
	}

	public PawPlanRuleException(EnumErroresInterface error, String message) {
		super(error, message);
	}

	public PawPlanRuleException(EnumErroresInterface error) {
		super(error);
	}

	public PawPlanRuleException(int codigo, String mensaje) {
		super(codigo, mensaje);
	}

	public PawPlanRuleException(int codigo, Throwable arg1) {
		super(codigo, arg1);
	}

	public PawPlanRuleException(int codigo) {
		super(codigo);
	}

	public PawPlanRuleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
}
