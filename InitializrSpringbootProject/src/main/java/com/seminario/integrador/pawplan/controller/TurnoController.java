package com.seminario.integrador.pawplan.controller;
/**
*
* @author sebastian
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.services.TurnoService;

@RestController
@RequestMapping(Constantes.URL_PATH_TURNO)
public class TurnoController {
	
	@Autowired
	private TurnoService turnoService;

	@RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.GET)
    public TurnoResponse consultarTurnosDisponible(TurnoRequest request) {
		
		return turnoService.getTurnosDisponibles(request.getFechaConsulta());
	}
	
	@RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.GET)
    public TurnoResponse reservarTurnos(TurnoRequest request) {
		
		return turnoService.reservarturno(request);
	}
	
}
