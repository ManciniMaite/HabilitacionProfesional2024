package com.seminario.integrador.pawplan.controller;
/**
*
* @author sebastian
*/

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.DisponibilidadRq;
import com.seminario.integrador.pawplan.controller.values.FiltroTurnoRq;
import com.seminario.integrador.pawplan.controller.values.PaginaTurnosRs;
import com.seminario.integrador.pawplan.controller.values.ReservarTurnoRq;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.services.TurnoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(Constantes.URL_PATH_TURNO)
public class TurnoController {
	
	@Autowired
	private TurnoService turnoService;

	@RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.POST)
    public TurnoResponse consultarTurnosDisponible(@RequestBody DisponibilidadRq request) throws JsonMappingException, JsonProcessingException, ParseException {
		System.out.println("FECHA REST: " + request.getFecha());
		return turnoService.getTurnosDisponibles(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_RESERVAR, method = RequestMethod.POST)
    public TurnoResponse reservarTurnos(@RequestBody ReservarTurnoRq request) {
		
		return turnoService.reservarTurno(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_CANCELAR, method = RequestMethod.POST)
    public TurnoResponse reservarCancelar(@RequestBody TurnoRequest request) {
		
		return turnoService.cancelarTurno(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_ACEPTAR, method = RequestMethod.POST)
    public TurnoResponse reservaAceptado(@RequestBody TurnoRequest request) {
		
		return turnoService.aceptarTurno(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_RECHAZAR, method = RequestMethod.POST)
    public TurnoResponse reservaRechazar(@RequestBody TurnoRequest request) {
		
		return turnoService.rechazarTurno(request);
	}
	
	@RequestMapping(value = Constantes.URL_PATH_ATENDER, method = RequestMethod.POST)
    public TurnoResponse reservaAtender(@RequestBody TurnoRequest request) {
		
		return turnoService.atenderTurno(request);
	}

	@RequestMapping(value = Constantes.URL_PATH_MIS_TURNOS, method = RequestMethod.POST)
	public PaginaTurnosRs getMisTurnos(@RequestBody FiltroTurnoRq request) {
		return turnoService.getMisTurnos(request);
	}

	@GetMapping("/{id}")
	public Turno getById(@PathVariable("id") Long id){
		return turnoService.getById(id);
	}
	
}
