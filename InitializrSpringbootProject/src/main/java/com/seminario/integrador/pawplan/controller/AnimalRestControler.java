/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.AnimalRq;
import com.seminario.integrador.pawplan.controller.values.AnimalRs;
import com.seminario.integrador.pawplan.controller.values.ListaAnimalesRs;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(Constantes.URL_PATH_ANIMALES)
public class AnimalRestControler {
    @Autowired
    private AnimalService service;
    
    
    @GetMapping("findByClient/{usuario}")
    public ListaAnimalesRs findByClient(@PathVariable("usuario") String usuario){
        return this.service.findByCliente(usuario);
    }
    
    @PostMapping("crear")
    public AnimalRs crearAnimal(@RequestBody AnimalRq rq){
        return this.service.crearAnimal(rq);
    }
    
    @PutMapping("update")
    public AnimalRs actualizarAnimal(@RequestBody AnimalRq rq){
        return this.service.updateAnimal(rq);
    }
    
    @DeleteMapping("delete/{id}")
    public Response delete(@PathVariable("id") Long id){
        return this.service.delete(id);
    }
}
