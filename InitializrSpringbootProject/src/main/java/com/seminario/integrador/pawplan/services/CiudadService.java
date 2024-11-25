/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.seminario.integrador.pawplan.services;

import com.seminario.integrador.pawplan.controller.values.CiudadesAdmRs;
import com.seminario.integrador.pawplan.model.Ciudad;
import com.seminario.integrador.pawplan.repository.CiudadRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author maite
 */
@Service
public class CiudadService {
    @Autowired
    private CiudadRepository repository;
    
    public CiudadesAdmRs findAll(){
        CiudadesAdmRs rs = new CiudadesAdmRs();
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        try{
            Iterable<Ciudad> iterableCiudad = this.repository.findAll(); // Obtiene el iterable
            for (Ciudad ciudad : iterableCiudad) {
                ciudades.add(ciudad); 
            }
            rs.setEstado("OK");
            rs.setCiudades(ciudades);
        } catch(Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al recuperar las ciudades");
        }
        
        return rs;
    }
    
    public Optional<Ciudad> findById(Long id){
        try {
            return this.repository.findById(id);
        } catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Ciudad no encontrada con id: " + id);
        }
    }  
}
