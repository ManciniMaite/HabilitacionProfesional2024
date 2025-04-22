/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.controller.values.VeterinariaXCiudad;
import com.seminario.integrador.pawplan.controller.values.VeterinarioXciudad;
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
        SELECT u.id, u.razon_social
        FROM usuario u
        JOIN domicilio d ON d.usuario_id = u.id
        JOIN ciudad c ON d.ciudad_id = c.id
        WHERE c.id = :idCiudad
        AND u.hace_domicilio = :domicilio
        AND TRIM(u.dtype) = 'Veterinaria';
    """, nativeQuery = true)    
    ArrayList<VeterinariaXCiudad> findByCiudad(@Param("idCiudad") Long idCiudad, @Param("domicilio") boolean domicilio);
}

