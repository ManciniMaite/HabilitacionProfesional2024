/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.Turno;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface TurnoRepository extends CrudRepository<Turno, Long>{
    
	@Query("select funcion(:date)")
	public ArrayList<Horario> consultarTurnosDisponibles(Date fecha);
	
	
}
