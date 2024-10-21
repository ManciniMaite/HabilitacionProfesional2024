package com.seminario.integrador.pawplan.controller;
/**
*
* @author sebastian
*/

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;

@RestController
@RequestMapping(Constantes.URL_PATH_TURNO)
public class TurnoController {

	@RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.GET)
    public TurnoResponse<?> consultar() {
		
		return null;
	}
}
