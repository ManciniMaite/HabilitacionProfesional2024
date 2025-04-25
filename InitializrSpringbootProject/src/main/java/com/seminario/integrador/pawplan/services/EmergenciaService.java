package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.Contactos;
import com.seminario.integrador.pawplan.controller.values.EmergenciaResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.enums.EnumEstadosGenerales;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.repository.DomicilioRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class EmergenciaService {
	
	private Logger logger = LoggerFactory.getLogger(EmergenciaService.class);
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DomicilioRepository domicilioRepository;
	
	 @Autowired
	 private VeterinarioRepository veterinarioRepository;
	
	public EmergenciaResponse emergencia() {
		
		EmergenciaResponse result = new EmergenciaResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		
		Cliente cliente;
		switch (usuario.getRole()) {
		case PACIENTE:
			cliente = (Cliente) usuario;
			break;
		
		default:
			
			//solo un cliente puede ver emergencias
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		//obtener direccion del usuario
		Domicilio domicilio = domicilioRepository.findByUsuarioId(cliente.getId()).get(0);
		
		//obtener veterianarias en horario laboral y que hagan guardias		
		List<Contactos> contactos = new ArrayList<>();
		for (Object[] row : veterinarioRepository.findByCiudadHorario(domicilio.getCiudad().getId()) ) {
		    contactos.add(new Contactos(
		        (String) row[0],
		        (String) row[1],
		        (String) row[2]
		    ));
		}
		
		result.setContactos(contactos);
		result.setEstado(String.valueOf(EnumEstadosGenerales.OK.getCodigo()));
		result.setMensaje(EnumEstadosGenerales.OK.getMensaje());
		
		return result;
	}
}
