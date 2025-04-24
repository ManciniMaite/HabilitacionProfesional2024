package com.seminario.integrador.pawplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.seminario.integrador.pawplan.controller.values.DomicilioRq;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.services.DomicilioService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/domicilio")
public class DomicilioRestController {
    @Autowired
    private DomicilioService service;

    @GetMapping("/findByClient/{dni}")
    public List<Domicilio> getByClient(@PathVariable("dni") String dni){
        return this.service.getByUsuario(dni);
    }
    
   @PostMapping("/crear")
    public Domicilio crear(@RequestBody    DomicilioRq rq) {
        return this.service.nuevDomicilio(rq);
    }

    @PutMapping("/update")
    public Domicilio actualizar(@RequestBody DomicilioRq rq){
        return this.service.actualizarDomicilio(rq);
    }

    @PostMapping("/delete")
    public Response deleteDomicilio(@RequestBody DomicilioRq rq){
        return this.service.deleteDomicilio(rq);
    }
}
