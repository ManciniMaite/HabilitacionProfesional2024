/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.controller.values.EspecieAdmRs;
import com.seminario.integrador.pawplan.services.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/especies")
public class EspecieRestController {
    @Autowired
    private EspecieService service;
    
    @GetMapping()
    public EspecieAdmRs findAll(){
        return this.service.getAll();
    }
}
