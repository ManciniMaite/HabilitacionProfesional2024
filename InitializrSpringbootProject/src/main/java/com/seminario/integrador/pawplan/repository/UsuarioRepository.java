/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author sebastian
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    
    public Usuario findByCorreo(String correo);
    
}
