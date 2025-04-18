package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;

import com.seminario.integrador.pawplan.model.DiaHorarioAtencion;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.security.Role;

import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class UsuarioRequest {
//COMUNES
    private Role tipoUsuario;
    private String telefono;
    private String correo;
    private String contrasenia;
    
//PACIENTE - VETERINARIO
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNac;
    
//PACIENTE
    private ArrayList<AnimalRq> animales;
    
//VETERINARIA
    private String razonSocial;
    private String cuit;
    private ArrayList<Veterinario> veterinarios;
	private boolean localFisico;

//VETERINARIA - VETERINARIO
    private boolean haceGuardia;
    private boolean aptoCirugia;
    @OneToMany
    private ArrayList<DiaHorarioAtencion> horario;
    private boolean haceDomicilio;
	private ArrayList<Long> tipoEspeciesIds;

//VETERINARIO
    private String matricula;
    private boolean esIndependiente;
    
    
//VETERINARIA - PACIENTE
    private DomicilioRq domicilio;
    

	public Role getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(Role tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public boolean isHaceGuardia() {
		return haceGuardia;
	}
	public void setHaceGuardia(boolean haceGuardia) {
		this.haceGuardia = haceGuardia;
	}
	public boolean isAptoCirugia() {
		return aptoCirugia;
	}
	public void setAptoCirugia(boolean aptoCirugia) {
		this.aptoCirugia = aptoCirugia;
	}
	public ArrayList<Veterinario> getVeterinarios() {
		return veterinarios;
	}
	public void setVeterinarios(ArrayList<Veterinario> veterinarios) {
		this.veterinarios = veterinarios;
	}
	public boolean isHaceDomicilio() {
		return haceDomicilio;
	}
	public void setHaceDomicilio(boolean heceDomicilio) {
		this.haceDomicilio = heceDomicilio;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public boolean isEsIndependiente() {
		return esIndependiente;
	}
	public void setEsIndependiente(boolean esIndependiente) {
		this.esIndependiente = esIndependiente;
	}
	public ArrayList<DiaHorarioAtencion> getHorario() {
		return horario;
	}
	public void setHorario(ArrayList<DiaHorarioAtencion> horario) {
		this.horario = horario;
	}
	
}
