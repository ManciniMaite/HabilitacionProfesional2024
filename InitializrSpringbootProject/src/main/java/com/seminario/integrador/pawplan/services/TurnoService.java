package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
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
	private ClienteRepository clienteRepository;

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
		String disponibilidad = turnoRepository.consultarTurnosDisponibles(vetId, turnoRequest.getFechaConsulta(),Integer.valueOf(turnoRequest.getDuracionEstimada()));
		ArrayList<Horario> horarios_disponibles = mapper.readValue(disponibilidad, mapper.getTypeFactory().constructCollectionType(List.class, Horario.class));
		result.setHorariosDisponibles(horarios_disponibles);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta disponibilidad Horaria ok.");
		
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
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		
		switch (usuario.getRole()) {
		case PACIENTE:
			Cliente cliente = (Cliente) usuario;
			turnoFinal.setCliente(cliente);
			break;
		case VETERINARIA:
			Veterinaria veterinaria = (Veterinaria) usuario;

			break;
		case VETERINARIO:
			Veterinario veterinario = (Veterinario) usuario;

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + usuario.getRole());
		}
		
		Estado estadoReservado = estadoRepository.findByNombre("RESERVADO").get(0);
		turnoFinal.setEstado(estadoReservado);
		
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

		result.setTurno(turnoRepository.save(turnoFinal));
		result.setEstadoReserva(estadoReservado);
		
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta disponibilidad Horaria ok.");
		return result;
		//return null;
		
		
		

	}
	
}
