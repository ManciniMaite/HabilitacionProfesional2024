package com.seminario.integrador.pawplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seminario.integrador.pawplan.controller.values.EmergenciaResponse;
import com.seminario.integrador.pawplan.services.EmergenciaService;

@RestController
public class EmergenciaRestController {
	
	@Autowired
    private EmergenciaService emergenciaService;
	
	@GetMapping("/Emergencia")
	public EmergenciaResponse emergencia() {
		
		return emergenciaService.emergencia();
		
	}
}
