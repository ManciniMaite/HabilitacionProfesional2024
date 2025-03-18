/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    
    public Usuario findByCorreo(String correo);

    @Query("SELECT u FROM Usuario u WHERE " +
       "(TYPE(u) = Cliente AND u.dni = :valor) OR " +
       "(TYPE(u) = Veterinario AND u.dni = :valor) OR " +
       "(TYPE(u) = Veterinaria AND u.cuit = :valor)")
    Optional<Usuario> findByDniOrCuit(@Param("valor") String valor);
    
}
