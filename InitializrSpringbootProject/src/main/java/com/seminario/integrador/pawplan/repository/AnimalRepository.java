/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Animal;
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
public interface AnimalRepository extends CrudRepository<Animal, Long> {
    List<Animal> findByCliente_IdAndEsActivoTrue(Long idCliente);

    @Query(value = """
        SELECT a.id FROM animal a WHERE a.id_cliente = :clienteId        
        """ ,
        nativeQuery = true)
    List<Long> findIdsByClienteId(@Param("clienteId") Long clienteId);

    @Query(value = """
        SELECT a.id FROM animal a WHERE a.id_cliente = :clienteId AND a.es_activo=true;        
        """ ,
        nativeQuery = true)
    List<Long> findIdsByClienteIdAndEsActivo(@Param("clienteId") Long clienteId);

}
