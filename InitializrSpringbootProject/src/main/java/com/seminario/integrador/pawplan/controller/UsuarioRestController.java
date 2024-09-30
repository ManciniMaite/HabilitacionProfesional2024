/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sebastian
 */
@RestController
public class UsuarioRestController {
    
    @Autowired(required = true)
    private AnimalRepository animalRepository;
    
    @RequestMapping(value = "/aludar", method = RequestMethod.GET)
    public String testeando() {
        Animal a = new Animal();
        a.setEsActivo(true);
        a.setNombre("carlos");
        a.setPeso(7);
        animalRepository.save(a);
        return "HOLA MUNDO";
    }
}
