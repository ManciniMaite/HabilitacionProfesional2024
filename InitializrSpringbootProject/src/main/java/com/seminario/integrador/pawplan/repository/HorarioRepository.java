/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Horario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface HorarioRepository extends CrudRepository<Horario, Long>{
    
}
