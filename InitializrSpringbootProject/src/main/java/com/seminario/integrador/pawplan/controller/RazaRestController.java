/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.controller.values.RazaAdmRs;
import com.seminario.integrador.pawplan.services.RazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/razas")
public class RazaRestController {
    @Autowired
    private RazaService service;
    
    @GetMapping("/{id}")
    public RazaAdmRs getByEspecie(@PathVariable("id") Long id){
        return this.service.getByEspecie(id);
    }
}
