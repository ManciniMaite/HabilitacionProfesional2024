package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.controller.values.TurnosResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.enums.EnumEstados;
import com.seminario.integrador.pawplan.enums.EnumEstadosGenerales;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.ClienteRepository;
import com.seminario.integrador.pawplan.repository.EstadoRepository;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.repository.VeterinariaRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class TurnoService {
	
	@Autowired
	private TurnoRepository turnoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	public TurnoResponse getTurnosDisponibles(TurnoRequest turnoRequest) throws JsonMappingException, JsonProcessingException{
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		long vetId = 0;
		if (turnoRequest.getVeterinariaId() != null) {
			vetId = turnoRequest.getVeterinariaId();
		} else if (turnoRequest.getVeterinarioId() != null) {
			vetId = turnoRequest.getVeterinarioId();
		} else {
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10001.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10001.getMensaje());
			return result;
		}
		
		// Tiempo de Consulta
		//int tiempo = 10;
		//if (turnoRequest.get)
		ObjectMapper mapper = Constantes.getObjectMapper();
		String disponibilidad = turnoRepository.consultarTurnosDisponibles(vetId, turnoRequest.getFechaConsulta());
		ArrayList<Horario> horarios_disponibles = mapper.readValue(disponibilidad, mapper.getTypeFactory().constructCollectionType(List.class, Horario.class));
		result.setHorariosDisponibles(horarios_disponibles);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta disponibilidad Horaria ok.");
		
		return result;
	}
	
	@Transactional( )
	public TurnoResponse reservarturno(TurnoRequest turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Turno turnoFinal = new Turno();
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		
		switch (usuario.getRole()) {
		case PACIENTE:
			Cliente cliente = (Cliente) usuario;
			//turnoFinal.setCliente(cliente);
			break;
		
		default:
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			return result;
		}
		
		Estado estadoReservado = estadoRepository.findByNombre(EnumEstados.RESERVADO.getNombre()).get(0);
		turnoFinal.setEstado(estadoReservado);
		
		turnoFinal.setFechaHoraReserva(new Date(System.currentTimeMillis()));
		turnoFinal.setFechaHora(turnoRequest.getFechaReserva());
		
		if (turnoRequest.getVeterinariaId() != null) {
			turnoFinal.setVeterinaria((veterinariaRepository.findById(turnoRequest.getVeterinariaId()).get()));
		}
		
		if (turnoRequest.getVeterinarioId() != null) {
			turnoFinal.setVeterinario((veterinarioRepository.findById(turnoRequest.getVeterinarioId()).get()));
		}
		
		turnoFinal.setDescripcionPublica(turnoRequest.getDescripcionPublica());
		
		turnoFinal.setEsADomicilio(turnoRequest.isEsADomicilio());

		result.setTurno(turnoRepository.save(turnoFinal));
		result.setEstadoReserva(estadoReservado);
		
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Reservar turno ok.");
		return result;
		//return null;
	}
	
	public TurnoResponse reservarCancelar(TurnoRequest turnoRequest) {
		
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Estado estadoCancelado = estadoRepository.findByNombre(EnumEstados.CANCELADO.getNombre()).get(0);
		
		Turno turno = turnoRepository.findById(turnoRequest.getTurnoId()).get();
		turno.setEstado(estadoCancelado);
		result.setTurno(turnoRepository.save(turno));
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Cancelar turno ok.");
		
		return result;
	}
	
	public TurnosResponse buscarTurnos(TurnoRequest turnoRequest) {
		
		TurnosResponse result = new TurnosResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}

		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		Estado estadoReservado = estadoRepository.findByNombre(EnumEstados.RESERVADO.getNombre()).get(0);
		List<Turno> turnos = new ArrayList<>();
		switch (usuario.getRole()) {
		case PACIENTE:
			Cliente cliente = (Cliente) usuario;

			break;
		case VETERINARIA:
			Veterinaria veterinaria = (Veterinaria) usuario;
			turnos = turnoRepository.findByVeterinariaAndEstado(veterinaria, estadoReservado);

			break;
		case VETERINARIO:
			Veterinario veterinario = (Veterinario) usuario;
			turnos = turnoRepository.findByVeterinarioAndEstado(veterinario, estadoReservado);

			break;
		default:
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			return result;
		}
		
		result.setTurnos(turnos);
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta turnos dados ok.");
		return result;
	}
	
}
