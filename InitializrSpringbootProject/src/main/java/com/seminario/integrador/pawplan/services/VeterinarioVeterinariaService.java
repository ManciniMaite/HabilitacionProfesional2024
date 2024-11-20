package com.seminario.integrador.pawplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class VeterinarioVeterinariaService {
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private TurnoRepository turnoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void /*TurnoResponse<T>*/ getVeterinariosDisponibles(TurnoRequest turnoRequest){
		
		PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
		//return null;
		
		
		
		
	}
}
