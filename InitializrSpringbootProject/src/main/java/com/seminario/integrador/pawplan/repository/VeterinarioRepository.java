/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.controller.values.ProfesionalPorVeterinaria;
import com.seminario.integrador.pawplan.controller.values.VeterinarioXciudad;
import com.seminario.integrador.pawplan.model.Veterinario;
import java.util.ArrayList;
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
public interface VeterinarioRepository extends CrudRepository<Veterinario, Long>{
    @Query(value = """
        SELECT 
			u.id,
			u.nombre,
			u.apellido
        FROM usuario u
        JOIN veterinario_tipo_especie ve ON ve.veterinario_id = u.id
        JOIN domicilio d ON d.usuario_id = u.id
        JOIN ciudad c ON d.ciudad_id = c.id
        WHERE c.id = :idCiudad
        AND ve.tipo_especie_id = :idTipoEspecie
        AND TRIM(u.dtype) = 'Veterinario';
    """, nativeQuery = true)
    ArrayList<VeterinarioXciudad> findByCiudad(@Param("idCiudad") Long idCiudad, @Param("idTipoEspecie") Long idTipoEspecie);

    // @Query("SELECT v.idUsuario as id, v.nombre as nombre, v.apellido as apellido, v.matricula as matricula " +
    //    "FROM Veterinaria vet JOIN vet.veterinarios v WHERE vet.idUsuario = :idVeterinaria")

    //    @Query(value = """
    //     SELECT 
	// 		u.id,
	// 		u.nombre,
	// 		u.apellido,
    //         u.matricula
    //     FROM Veterinaria vet JOIN vet.veterinarios v 
    //     WHERE vet.idUsuario = :idVeterinaria
    //     AND TRIM(u.dtype) = 'Veterinario';
    // """, nativeQuery = true)   
    // List<ProfesionalPorVeterinaria> findVeterinariosByVeterinariaId(@Param("idVeterinaria") Long idVeterinaria);


    List<ProfesionalPorVeterinaria> findByVeterinariaId(@Param("idVeterinaria") Long idVeterinaria);
}


