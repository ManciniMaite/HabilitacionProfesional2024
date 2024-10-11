/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;

import java.util.Date;

/**
 *
 * @author sebastian
 */
public class PrincipalPawplan {
    
    private long clienteId;
    private Role role;
    private Date loginDate;
    private long loginDateExpiration;

    public PrincipalPawplan() {
    }

    public PrincipalPawplan(long clienteId, Role role, Date loginDate, long loginDateExpiration) {
        this.clienteId = clienteId;
        this.role = role;
        this.loginDate = loginDate;
        this.loginDateExpiration = loginDateExpiration;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public long getLoginDateExpiration() {
        return loginDateExpiration;
    }

    public void setLoginDateExpiration(long loginDateExpiration) {
        this.loginDateExpiration = loginDateExpiration;
    }
    
    
}
