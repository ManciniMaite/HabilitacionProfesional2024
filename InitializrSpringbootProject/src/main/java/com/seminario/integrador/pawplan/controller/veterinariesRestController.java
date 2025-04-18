/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.controller.values.VeterinarioVeterinariaResponse;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.services.VeterinarioVeterinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/veterinaries")
public class veterinariesRestController {
    @Autowired
    VeterinarioVeterinariaService service;
    
    @GetMapping("/{idCiudad}/{idTipoEspecie}")
    public VeterinarioVeterinariaResponse getByCiudad(@PathVariable("idCiudad") Long idCiudad,@PathVariable("idTipoEspecie") Long idTipoEspecie ){
        return this.service.getByCiudad(idCiudad, idTipoEspecie );
    }

    @GetMapping("/getVeterinariosPorVeterinaria")
    public Response getProfesionalesPorVeterinaria(){
        return this.service.getProfesionales();
    }


    @DeleteMapping("/quitarProfesional/{idProfesional}")
    public Response quitarProfesional(@PathVariable("idProfesional") Long idProfesional ){
        return this.service.quitarVeterinarioDeVeterinaria(idProfesional);
    }

    @PostMapping("/agregarProfesional/{idProfesional}")
    public Response agregarProfesional(@PathVariable("idProfesional") Long idProfesional ){
        return this.service.addVeterinarioAVeterinaria(idProfesional);
    }

    @GetMapping("/{dni}")
    public Usuario getByDni(@PathVariable("dni") String dni ){
        return this.service.getByDni(dni);
    }
}
