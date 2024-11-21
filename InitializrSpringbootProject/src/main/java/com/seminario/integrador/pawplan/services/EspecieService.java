/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.seminario.integrador.pawplan.services;

import com.seminario.integrador.pawplan.controller.values.EspecieAdmRs;
import com.seminario.integrador.pawplan.repository.EspecieRepository;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.model.Especie;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author maite
 */
@Service
public class EspecieService {
    @Autowired
    private EspecieRepository repository;
    
    public EspecieAdmRs getAll(){
        EspecieAdmRs rs = new EspecieAdmRs();
        ArrayList<Especie> especies = new ArrayList<>();
        try{
            Iterable<Especie> iterableEspecies = this.repository.findAll(); // Obtiene el iterable
            for (Especie especie : iterableEspecies) {
                especies.add(especie); 
            }
            rs.setEspecies(especies);
            rs.setEstado("OK");
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al recuperar las especies");
        }
        return rs;
    }
}
