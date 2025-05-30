/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Domicilio;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 *
 * @author sebastian
 */
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    // @Query("SELECT c.domicilios FROM cliente c WHERE c.dni = :dni")
    // ArrayList<Domicilio> findDomiciliosByCliente(@Param("dni") String dni);

    List<Cliente> findBfindByDni(String dni);
}
