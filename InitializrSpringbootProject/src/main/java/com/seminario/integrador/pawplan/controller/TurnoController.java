package com.seminario.integrador.pawplan.controller;
/**
*
* @author sebastian
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.services.TurnoService;

@RestController
@RequestMapping(Constantes.URL_PATH_TURNO)
public class TurnoController {
	
	@Autowired
	private TurnoService turnoService;

	@RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.POST)
    public TurnoResponse consultarTurnosDisponible(@RequestBody TurnoRequest request) throws JsonMappingException, JsonProcessingException {
		
		return turnoService.getTurnosDisponibles(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_RESERVAR, method = RequestMethod.POST)
    public TurnoResponse reservarTurnos(@RequestBody TurnoRequest request) {
		
		return turnoService.reservarturno(request);
	}
	
}
