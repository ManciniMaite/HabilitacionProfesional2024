package com.seminario.integrador.pawplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.controller.values.ValidarMatriculaRq;
import com.seminario.integrador.pawplan.services.MatriculaService;



@RestController
@RequestMapping(Constantes.URL_PATH_VALIDAR_MATRICULA)
public class MatriculaRestController {
    @Autowired
    private MatriculaService matriculaService;
    @PostMapping("")
    public Response validarMatricula(@RequestBody ValidarMatriculaRq validarMatriculaRq){
        return this.matriculaService.validarMatricula(validarMatriculaRq);
    }
}
