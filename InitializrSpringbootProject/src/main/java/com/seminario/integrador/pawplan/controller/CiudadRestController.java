/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.controller.values.CiudadesAdmRs;
import com.seminario.integrador.pawplan.repository.CiudadRepository;
import com.seminario.integrador.pawplan.services.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/ciudades")
public class CiudadRestController {
    @Autowired
    private CiudadService service;
    
    @GetMapping()
    public CiudadesAdmRs getAll(){
        return this.service.findAll();
    }
    
}
