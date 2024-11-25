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
    @Query(value = "SELECT * FROM veterinaria v JOIN domicilio d ON v.id_domicilio = d.id WHERE d.id_ciudad = :idCiudad", nativeQuery = true)
    ArrayList<Veterinaria> findByCiudad(@Param("idCiudad") Long idCiudad);
}