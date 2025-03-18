/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.SessionManagerRequest;
import com.seminario.integrador.pawplan.controller.values.SessionManagerResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.exception.PawPlanRuleException;
import com.seminario.integrador.pawplan.security.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sebastian
 */
@RestController
public class SessionRestController {
	
	@Autowired
	private SessionManager sessionManager;

    @RequestMapping(value=Constantes.URL_PATH_SESSION_MANAGER, method=RequestMethod.POST, produces="application/json", consumes = "application/json")
    public SessionManagerResponse login(@RequestBody SessionManagerRequest loginRequest) {
    	
    	SessionManagerResponse loginResponse = null;
    	

		try {
                        loginResponse = new SessionManagerResponse();
			loginResponse = sessionManager.login(loginRequest.getCorreo(), loginRequest.getPassword());
                        loginResponse.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_200.getEstado()));
                        loginResponse.setMensaje(String.valueOf(EnumCodigoErrorLogin.LOGIN_200.getMensaje()));
                        
		} catch (PawPlanRuleException e) {
			loginResponse = new SessionManagerResponse();
                        loginResponse.setEstado(String.valueOf(e.getCodigo()));
                        loginResponse.setMensaje(e.getMessage());
                        
		}
        
    	return loginResponse;
    }
}
