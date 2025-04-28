/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller;

import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.UsuarioRequest;
import com.seminario.integrador.pawplan.controller.values.UsuarioResponse;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.services.DomicilioService;
import com.seminario.integrador.pawplan.services.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sebastian
 */
@RestController
@RequestMapping(Constantes.URL_PATH_USUARIO)
public class UsuarioRestController {
    
	Logger logger = LoggerFactory.getLogger(UsuarioRestController.class);
	
    @Autowired(required = true)
    private UsuarioService usuarioService;

	@Autowired
	private DomicilioService domicilioService;
    
    @RequestMapping(value = Constantes.URL_PATH_CONSULTAR, method = RequestMethod.GET)
    public UsuarioResponse<?> consultar() {
		
    	UsuarioResponse<ArrayList<?>> result;
    	try {
    		result = usuarioService.consultar();
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al obtener todos los usuarios - 01. " + e.getMessage());
			logger.error("ERROR AL CONSULTAR USUARIOS",e);
		}
    	
    	return result;
       
    }
    
    @RequestMapping(value = Constantes.URL_PATH_CREAR, method = RequestMethod.POST)
    public UsuarioResponse<?> crear(@RequestBody UsuarioRequest usuarioRequest) {
		
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.CrearUsuario(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al crear un usuarios - 02. " + e.getMessage());
			logger.error("ERROR AL crear USUARIOS",e);
		}
    	
    	return result;
       
    }
    
    
    @RequestMapping(value = Constantes.URL_PATH_MODIFICAR, method = RequestMethod.POST)
    public UsuarioResponse<?> modificar(@RequestBody UsuarioRequest usuarioRequest) {
		
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.modificarUsuario(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al crear un usuarios - 02. " + e.getMessage());
			logger.error("ERROR AL modificar USUARIOS",e);
		}
    	
    	return result;
       
    }
    
    @RequestMapping(value = Constantes.URL_PATH_ELIMINAR, method = RequestMethod.POST)
    public UsuarioResponse<?> eliminar(@RequestBody UsuarioRequest usuarioRequest) {
		
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.eliminarUsuario(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al crear un usuarios - 02. " + e.getMessage());
			logger.error("ERROR AL eliminsar USUARIOS",e);
		}
    	
    	return result;
       
    }
    
    
    @GetMapping("/domicilios/{dniCliente}")
    public List<Domicilio> getDomicilioByCliente(@PathVariable String dniCliente){
        return this.domicilioService.getByUsuario(dniCliente);
    }
    
    @RequestMapping(value = Constantes.URL_PATH_RECUPERAR_CONTRASENA, method = RequestMethod.POST)
    public UsuarioResponse<?> recuperarContraseña(@RequestBody UsuarioRequest usuarioRequest) {
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.recuperarContrasena(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al recuperar pregunta secreta - 05. " + e.getMessage());
			logger.error("ERROR recuperar pregunta secreta",e);
		}
    	
    	return result;
    }
    
    @RequestMapping(value = Constantes.URL_PATH_PREGUNTA_SECRETA, method = RequestMethod.POST)
    public UsuarioResponse<?> preguntaSecreta(@RequestBody UsuarioRequest usuarioRequest) {
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.preguntaSecreta(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al comparar pregunta secreta - 05. " + e.getMessage());
			logger.error("ERROR al comparar pregunta secreta",e);
		}
    	
    	return result;
    }
    
    @RequestMapping(value = Constantes.URL_PATH_NUEVA_CONTRASENA, method = RequestMethod.POST)
    public UsuarioResponse<?> nuevaContraseña(@RequestBody UsuarioRequest usuarioRequest) {
    	UsuarioResponse<?> result;
    	try {
    		result = usuarioService.nuevaContrasena(usuarioRequest);
		} catch (Exception e) {
			result = new UsuarioResponse<>();
			result.setEstado("ERROR");
			result.setMensaje("Ocurrio un error al cambiar contraseña - 05. " + e.getMessage());
			logger.error("ERROR al cambiar contraseña",e);
		}
    	
    	return result;
    }
    
}
