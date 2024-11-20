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

    LOGIN_200(200,"Ok."),
    LOGIN_401(401,"No Autorizado."),
    LOGIN_2000(2000,"Error interno procesando request."),
    LOGIN_2400(2400,"Credenciales de login invalidas."),
    LOGIN_2410(2410,"El usuario esta deshabilitado."),
    LOGIN_2420(2420,"La session caduco.");
    
    private final int codigo;
    private final String mensaje;

    private EnumCodigoErrorLogin(int codigo, String mensaje) {
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
