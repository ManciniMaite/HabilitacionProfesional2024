/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;

import org.springframework.lang.Nullable;

/**
 *
 * @author sebastian
 */
public enum Role {

    PACIENTE("PACIENTE", "Usuario como paciente o cliente"),
    VETERINARIO("VETERINARIO", "Usuario como medico veterinario"),
    VETERINARIA("VETERINARIA", "Usuario como local veterinaria"),
    PLATFORM_ADMIN("PLATFORM_ADMIN", "Administrador de la plataforma.");
    
    private final String role;
    private final String descripcion;

    private Role(String role, String descripcion) {
        this.role = role;
        this.descripcion = descripcion;
    }

    public String getRole() {
        return role;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Nullable
    public static Role resolve(String roleCode) {
        for (Role role : values()) {
            if (role.role.equalsIgnoreCase(roleCode)) {
                return role;
            }
        }
        return null;
    }
}
