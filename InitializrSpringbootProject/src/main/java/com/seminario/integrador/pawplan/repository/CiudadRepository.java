/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Ciudad;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author maite
 */
public interface CiudadRepository extends CrudRepository<Ciudad, Long> {
    
}
