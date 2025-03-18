/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.controller.values.AnimalRs;
import com.seminario.integrador.pawplan.controller.values.ListaAnimalesRs;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/animal/")
public class AnimalRestControler {
    @Autowired
    private AnimalService service;
    
    
    @GetMapping("findByClient")
    public ListaAnimalesRs findByClient(){
        return this.service.findByCliente();
    }
    
    @PostMapping("crear")
    public AnimalRs crearAnimal(Animal rq){
        return this.service.crearAnimal(rq);
    }
    
    @PutMapping("update")
    public AnimalRs actualizarAnimal(Animal rq){
        return this.service.UpdateAnimal(rq);
    }
    
    @DeleteMapping("delete")
    public Response delete(Long id){
        return this.service.delete(id);
    }
}
