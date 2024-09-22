/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sebastian
 */
@RestController
public class UsuarioRestController {
    
    @RequestMapping(value = "/aludar", method = RequestMethod.POST)
    public String testeando() {
        return "HOLA MUNDO";
    }
}
