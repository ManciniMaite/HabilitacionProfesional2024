/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.seminario.integrador.pawplan.services;

import com.seminario.integrador.pawplan.controller.values.RazaAdmRs;
import com.seminario.integrador.pawplan.model.Raza;
import com.seminario.integrador.pawplan.repository.RazaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author maite
 */
@Service
public class RazaService {
    @Autowired
    private RazaRepository repository;
    
    public RazaAdmRs getByEspecie(Long id){
        RazaAdmRs rs = new RazaAdmRs();
        try{
            List<Raza> razas = this.repository.findByEspecie_Id(id);
            rs.setEstado("OK");
            rs.setRazas(razas);
        } catch(Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al intentar recuperar las razas");
        }
        return rs;
    }
}
