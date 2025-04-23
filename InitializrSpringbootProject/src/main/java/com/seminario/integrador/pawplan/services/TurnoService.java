package com.seminario.integrador.pawplan.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.DisponibilidadRq;
import com.seminario.integrador.pawplan.controller.values.FiltroTurnoRq;
import com.seminario.integrador.pawplan.controller.values.PaginaTurnosRs;
import com.seminario.integrador.pawplan.controller.values.ReservarTurnoRq;
import com.seminario.integrador.pawplan.controller.values.TurnoFb;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.TurnoResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.enums.EnumEstados;
import com.seminario.integrador.pawplan.enums.EnumEstadosGenerales;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.AnimalRepository;
import com.seminario.integrador.pawplan.repository.ClienteRepository;
import com.seminario.integrador.pawplan.repository.EstadoRepository;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
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
	private EstadoRepository estadoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	@Autowired
	private AnimalRepository animalRepository;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	public TurnoResponse getTurnosDisponibles(DisponibilidadRq turnoRequest) throws JsonMappingException, JsonProcessingException{
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		long vetId = 0;
		if (turnoRequest.getVeterinarioId() != null && turnoRequest.getVeterinarioId() != 0) {
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
		//System.out.println( "Id vet: " + vetId + " FECHA: " + turnoRequest.getFecha());
		String disponibilidad = turnoRepository.consultarTurnosDisponibles(vetId, turnoRequest.getFecha());
		//System.out.println("DISPONIBILIDAD: " + disponibilidad);
		ArrayList<Horario> horarios_disponibles = mapper.readValue(disponibilidad, mapper.getTypeFactory().constructCollectionType(List.class, Horario.class));
		result.setHorariosDisponibles(horarios_disponibles);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta disponibilidad Horaria ok.");
		
		return result;
	}
	
	@Transactional( )
	public TurnoResponse reservarTurno(ReservarTurnoRq turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			System.out.println("session expirada");
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
		//System.out.println("FECHA RESERVAR: " + turnoRequest.getFecha());
		

        Instant instantFecha = Instant.parse(turnoRequest.getFecha());
        ZonedDateTime zdtFecha = instantFecha.atZone(ZoneId.systemDefault());
        LocalDate fechaLocal = zdtFecha.toLocalDate();

        LocalTime hora = LocalTime.parse(turnoRequest.getHora()); 

        LocalDateTime fechaHoraLocal = LocalDateTime.of(fechaLocal, hora);
        ZonedDateTime zdtFinal = fechaHoraLocal.atZone(ZoneId.systemDefault());

		turnoFinal.setFechaHora( Date.from(zdtFinal.toInstant()));
		
		if (turnoRequest.getVeterinariaId() != null && turnoRequest.getVeterinariaId() != 0) {
			turnoFinal.setVeterinaria((veterinariaRepository.findById(turnoRequest.getVeterinariaId()).get()));
		}
		
		if (turnoRequest.getVeterinarioId() != null && turnoRequest.getVeterinarioId() != 0) {
			turnoFinal.setVeterinario((veterinarioRepository.findById(turnoRequest.getVeterinarioId()).get()));
		}
		Animal animal = animalRepository.findById(turnoRequest.getAnimalId()).get();
		if (animal == null ){
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			return result;
		}
		turnoFinal.setAnimal(animal);
		
		//turnoFinal.setDescripcionPublica(turnoRequest.getDescripcionPublica());
		
		turnoFinal.setEsADomicilio(turnoRequest.isEsDomicilio());

		//se puede guardar el turno??
		// busco estados
		List<String> estados = new ArrayList<>();
		estados.add(EnumEstados.RESERVADO.getNombre());
		estados.add(EnumEstados.ACEPTADO.getNombre());
		estados.add(EnumEstados.ATENDIDO.getNombre());
		//busco los turnos
		List<Turno> turnosObtenidos = turnoRepository.buscarTurnosPorVeterinariaVeterinarioYFechaYEstado(
				turnoFinal.getVeterinario(),
				turnoFinal.getVeterinaria(),
				turnoFinal.getFechaHora(),
				estados
				);

		if(!turnosObtenidos.isEmpty()) {
			result.setEstado(EnumEstadosGenerales.ERROR.getEstado());
			result.setMensaje("Turno ya registrado");
			return result;
		}
		
		try{
			turnoRepository.save(turnoFinal);
		} catch(Exception e){
			e.printStackTrace();
			result.setEstado(EnumEstadosGenerales.ERROR.getEstado());
			result.setMensaje("Ocurrio un error al guardar el turno");
			return result;
		}
		
		//result.setTurno();
		result.setEstadoReserva(estadoReservado);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Reservar turno ok.");
		return result;
		//return null;
	}
	
	public TurnoResponse cancelarTurno(TurnoRequest turnoRequest) {
		
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Estado estadoCancelado = estadoRepository.findByNombre(EnumEstados.CANCELADO.getNombre()).get(0);
		
		result.setTurno(cambiarEstadoTurno(turnoRequest, estadoCancelado));
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Cancelar turno ok.");
		
		return result;
	}
	
	public TurnoResponse buscarTurnos(TurnoRequest turnoRequest) {
		
		TurnoResponse result = new TurnoResponse();
		
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
	
	public TurnoResponse aceptarTurno(TurnoRequest turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() == Role.PACIENTE) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Estado estado = estadoRepository.findByNombre(EnumEstados.ACEPTADO.getNombre()).get(0);
		
		result.setTurno(cambiarEstadoTurno(turnoRequest, estado));
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno ACEPTADO ok.");
		
		
		return result;
	}
	
	public TurnoResponse rechazarTurno(TurnoRequest turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() == Role.PACIENTE) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Estado estado = estadoRepository.findByNombre(EnumEstados.RECHAZADO.getNombre()).get(0);
		
		result.setTurno(cambiarEstadoTurno(turnoRequest, estado));
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno RECHAZADO ok.");
		
		
		return result;
	}
	
	public TurnoResponse atenderTurno(TurnoRequest turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() == Role.PACIENTE) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Estado estado = estadoRepository.findByNombre(EnumEstados.ATENDIDO.getNombre()).get(0);
		
		result.setTurno(cambiarEstadoTurno(turnoRequest, estado));
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno RECHAZADO ok.");
		
		
		return result;
	}
	
	public Turno cambiarEstadoTurno(TurnoRequest turnoRequest, Estado estado) {
		Turno turno = turnoRepository.findById(turnoRequest.getTurnoId()).get();
		turno.setEstado(estado);
		return turnoRepository.save(turno);
	}

	public PaginaTurnosRs getMisTurnos (FiltroTurnoRq turnoRequest){

		PaginaTurnosRs rs = new PaginaTurnosRs();

		// System.out.println("NEstado: " + turnoRequest.getNEstado());
		// System.out.println("Fecha recibida: " + turnoRequest.getFecha());
		// System.out.println("Cliente ID: " + turnoRequest.getIdCliente());
		// System.out.println("Animal ID: " + turnoRequest.getIdAnimal());
		// System.out.println("Veterinario ID: " + turnoRequest.getIdVeterinario());

		
		//OBTENER US SESSION
		PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
		Role role = Role.resolve(principalPawplan.getRole().toString());

		//SET ID SEGUN CORRESPONDA
		if (role != null) {
			switch (role) {
				case VETERINARIA:
					turnoRequest.setIdVeterinaria(principalPawplan.getClienteId());
					break;
				case VETERINARIO:
					turnoRequest.setIdVeterinario(principalPawplan.getClienteId());
					break;
				case PACIENTE:
					turnoRequest.setIdCliente(principalPawplan.getClienteId());
					break;
				default:
					rs.setEstado("ERROR");
					rs.setMensaje("Rol de Session no valido!");
					return rs;
			}
		} else {
			rs.setEstado("ERROR");
			rs.setMensaje("Rol de Session invalido!");
			return rs;
		}

		//buscar estado que venga en la rq
		Estado estado = estadoRepository.findByNombre(turnoRequest.getNEstado()).get(0);
		
		//fecha sin hora 
		// java.sql.Date fechaSinHora = null;
		// if (turnoRequest.getFecha() != null) {
		// 	fechaSinHora = new java.sql.Date(turnoRequest.getFecha().getTime());
		// }

		LocalDate fechaSinHora = null;
		if (turnoRequest.getFecha() != null) {
			fechaSinHora = turnoRequest.getFecha().toInstant()
									.atZone(ZoneId.systemDefault())
									.toLocalDate();
		}

		//listado de id de los animales del cliente
		List<Long> animalIds = null;
		if (turnoRequest.getIdAnimal() != null && turnoRequest.getIdAnimal() != 0) {
			//si hay un id especifico entonces buscamos por ese
			animalIds = List.of(turnoRequest.getIdAnimal());
		} else if (turnoRequest.getIdCliente() != null && turnoRequest.getIdCliente() != 0) {
			//si no hay un id especifico de animal y si hay de cliente entonces buscamos todos los animales que pertenecen a este cliente
			animalIds = animalRepository.findIdsByClienteId(turnoRequest.getIdCliente());
		}

		String animalesId = animalIds.stream().map(Object::toString).collect(Collectors.joining(","));  //[1,2] -> "1,2"

		List<TurnoFb> turnos = null;
		Long total = 0l;
		System.out.println(animalIds);

		try {

			System.out.println("idEstado: " + estado.getId());
			System.out.println("Fecha recibida: " + turnoRequest.getFecha());
			System.out.println("Cliente ID: " + turnoRequest.getIdCliente());
			System.out.println("Animales: " + animalesId);
			System.out.println("Veterinario ID: " + turnoRequest.getIdVeterinario());


			turnos = turnoRepository.buscarTurnosPagina(
				animalesId, 
				turnoRequest.getIdVeterinaria(), 
				turnoRequest.getIdVeterinario(), 
				estado.getId(), 
				fechaSinHora, 
				turnoRequest.getPage(), turnoRequest.getSize(), turnoRequest.getOrderBy(), turnoRequest.getOrderDir());
    		total = turnoRepository.contarTurnos(
				animalesId, 
				turnoRequest.getIdVeterinaria(), 
				turnoRequest.getIdVeterinario(), 
				estado.getId(), 
				fechaSinHora
			);


		} catch (Exception e) {
			e.printStackTrace();
			rs.setTurnos(turnos);
			rs.setEstado("ERROR");
			rs.setMensaje("Ocurrio un error al filtrar los turnos");
		}
		
		

		rs.setTurnos(turnos);
		rs.setTotal(total);
		rs.setEstado("OK");
		rs.setMensaje("Consulta ok!");
		return rs;
	}


	public Turno getById(Long id){
		Optional<Turno> ot = turnoRepository.findById(id);
		if(ot.isPresent()){
			Turno t = ot.get();
			t.getVeterinario().setHorarios(null);
			if(t.getVeterinaria()!=null){
				t.getVeterinaria().setHorarioAtencion(null);
			}
			return t;
		}else{
			return null; 
		}
	}

}
