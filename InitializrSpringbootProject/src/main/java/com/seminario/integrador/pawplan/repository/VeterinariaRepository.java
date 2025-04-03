/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Veterinaria;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface VeterinariaRepository extends CrudRepository<Veterinaria, Long>{
    @Query(value = """
        SELECT u.*
        FROM usuario u
        JOIN domicilio d ON d.usuario_id = u.id
        JOIN ciudad c ON d.ciudad_id = c.id
        WHERE c.id = 2
        AND TRIM(u.dtype) = 'Veterinaria';
    """, nativeQuery = true)    
    ArrayList<Veterinaria> findByCiudad(@Param("idCiudad") Long idCiudad);
}