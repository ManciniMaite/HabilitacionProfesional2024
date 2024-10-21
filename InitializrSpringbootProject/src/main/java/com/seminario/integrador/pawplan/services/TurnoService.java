package com.seminario.integrador.pawplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.repository.TurnoRepository;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository turnoRepository;
	
	
	public void /*TurnoResponse<T>*/ getTurnosDisponibles(){
		
		//return null;
	}
	
}
