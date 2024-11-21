package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;

import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.DiaHorarioAtencion;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.model.Especialidad;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.security.Role;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

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
    private ArrayList<Animal> animales;
    
//VETERINARIA
    private String razonSocial;
    private String cuit;
    private ArrayList<Veterinario> veterinarios;

//VETERINARIA - VETERINARIO
    private boolean haceGuardia;
    private boolean aptoCirugia;
    private ArrayList<DiaHorarioAtencion> horarioAtencion;
    @OneToMany
    private ArrayList<DiaHorarioAtencion> horario;
    private boolean haceDomicilio;

//VETERINARIO
    private int matricula;
    private boolean esIndependiente;
    @OneToMany
    private ArrayList<Especialidad> especialidad;
    
//VETERINARIA - PACIENTE
    private Domicilio domicilio;
    
    
    
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
	public ArrayList<Animal> getAnimales() {
		return animales;
	}
	public void setAnimales(ArrayList<Animal> animales) {
		this.animales = animales;
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
	public ArrayList<DiaHorarioAtencion> getHorarioAtencion() {
		return horarioAtencion;
	}
	public void setHorarioAtencion(ArrayList<DiaHorarioAtencion> horarioAtencion) {
		this.horarioAtencion = horarioAtencion;
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
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
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
	public ArrayList<Especialidad> getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(ArrayList<Especialidad> especialidad) {
		this.especialidad = especialidad;
	}

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
        
    
    
    
    
}
