/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.model;


import com.seminario.integrador.pawplan.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author sebastian
 */
@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String telefono;
    
    private Role role;
    
    @Column(unique = true,  nullable = true)
    private String correo;
    private String contrasenia;
    
    private String pregunta;
    private String respuesta;

    private boolean isActivo = true;
    
    @PrePersist
    protected void onCreate() {
        if (isActivo == false) {
            isActivo = true;  // Establece el valor por defecto como true si no se especifica
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

	public boolean isActivo() {
		return isActivo;
	}

	public void setActivo(boolean esActivo) {
		this.isActivo = esActivo;
	}

    
    
    
}
