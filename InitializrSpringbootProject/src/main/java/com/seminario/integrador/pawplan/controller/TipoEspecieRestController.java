package com.seminario.integrador.pawplan.controller;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seminario.integrador.pawplan.model.TipoEspecie;
import com.seminario.integrador.pawplan.repository.TipoEspecieRepository;;


@RestController()
@RequestMapping("/tipoEspecie")
public class TipoEspecieRestController {
    @Autowired
    private TipoEspecieRepository tipoEspecieRepository;

    @GetMapping("")
    public Iterable<TipoEspecie> getAll() {
        return this.tipoEspecieRepository.findAll();
    }
    
}
