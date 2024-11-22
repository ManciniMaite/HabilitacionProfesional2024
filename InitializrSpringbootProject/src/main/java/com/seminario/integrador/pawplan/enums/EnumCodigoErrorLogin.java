/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.enums;

/**
 *
 * @author sebastian
 */
public enum EnumCodigoErrorLogin implements EnumErroresInterface{

    LOGIN_200(200,"OK","OK"),
    LOGIN_401(401,"No Autorizado.","ERROR"),
    LOGIN_2000(2000,"Error interno procesando request.","ERROR"),
    LOGIN_2400(2400,"Credenciales de login invalidas.","ERROR"),
    LOGIN_2410(2410,"El usuario esta deshabilitado.","ERROR"),
    LOGIN_2420(2420,"La session caduco.","ERROR");
    
    private final int codigo;
    private final String mensaje;
    private final String estado;

    private EnumCodigoErrorLogin(int codigo, String mensaje, String estado) {
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
