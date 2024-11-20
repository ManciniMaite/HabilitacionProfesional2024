package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.repository.ClienteRepository;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.VeterinariaRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.Role;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository turnoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	public TurnoResponse getTurnosDisponibles(Date fecha){
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		result.setHorariosDisponibles(turnoRepository.consultarTurnosDisponibles(fecha));
		
		return result;
	}
	
	public TurnoResponse reservarturno(TurnoRequest turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Turno turnoFinal = new Turno();
		
		turnoFinal.setCliente((clienteRepository.findById(session.getClienteId())).get());
		turnoFinal.setFechaHoraReserva(new Date(System.currentTimeMillis()));
		turnoFinal.setFecha(turnoRequest.getFechaReserva());
		
		if (turnoRequest.getVeterinariaId() != null) {
			turnoFinal.setVeterinaria((veterinariaRepository.findById(turnoRequest.getVeterinariaId()).get()));
		}
		
		if (turnoRequest.getVeterinarioId() != null) {
			turnoFinal.setVeterinario((veterinarioRepository.findById(turnoRequest.getVeterinarioId()).get()));
		}
		
		turnoFinal.setDescripcion(turnoRequest.getDescripcion());
		turnoFinal.setDetalleTurno(turnoRequest.getDetalleTurno());
		turnoFinal.setDuracionEstimada(turnoRequest.getDuracionEstimada());
		turnoFinal.setEsADomicilio(turnoRequest.isEsADomicilio());
		turnoFinal.setEsGuardia(turnoRequest.isEsGuardia());
		
		turnoFinal.setMonto(turnoRequest.getMonto());
		
		return result;
	}
	
}
