/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface TurnoRepository extends CrudRepository<Turno, Long>{
    
	@Query("select calcular_turnos_disponibles(:clienteId, :fecha )")
	public String consultarTurnosDisponibles(@Param("clienteId") Long clienteId,@Param("fecha") Date fecha);
	
	List<Turno> findByClienteAndEstado(Cliente cliente, Estado estado);

    List<Turno> findByVeterinariaAndEstado(Veterinaria veterinaria, Estado estado);

    List<Turno> findByVeterinarioAndEstado(Veterinario veterinario, Estado estado);

	
}
