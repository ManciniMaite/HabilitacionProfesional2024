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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.seminario.integrador.pawplan.controller.values.AnimalDTO;
import com.seminario.integrador.pawplan.controller.values.AtenderTurnoRq;
import com.seminario.integrador.pawplan.controller.values.ClienteDTO;
import com.seminario.integrador.pawplan.controller.values.ClientesDeVeterinarieRs;
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
	
	private Logger logger = LoggerFactory.getLogger(TurnoService.class);
	
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
	
	@Transactional(rollbackFor = Throwable.class)
	public TurnoResponse getTurnosDisponibles(DisponibilidadRq turnoRequest) throws JsonMappingException, JsonProcessingException{
		TurnoResponse result = new TurnoResponse();
		
		//VERIFICAMOS LA SSESION
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		//CONTROL VETERINARIA VETERINARIO
		long vetId = 0;
		if (turnoRequest.getVeterinarioId() != null && turnoRequest.getVeterinarioId() != 0) {
			vetId = turnoRequest.getVeterinarioId();
		} else {
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10001.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10001.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		// OBTENEMOS LA DISPONIBILIDAD HORARIA
		// Tiempo de Consulta
		//int tiempo = 10;
		//if (turnoRequest.get)
		ObjectMapper mapper = Constantes.getObjectMapper();
		logger.debug( "Id vet: " + vetId + " FECHA: " + turnoRequest.getFecha());
		
		//System.out.println( "Id vet: " + vetId + " FECHA: " + turnoRequest.getFecha());
		String disponibilidad = turnoRepository.consultarTurnosDisponibles(vetId, turnoRequest.getFecha());
		logger.debug("DISPONIBILIDAD: " + disponibilidad);
		
		//System.out.println("DISPONIBILIDAD: " + disponibilidad);
		ArrayList<Horario> horarios_disponibles = mapper.readValue(disponibilidad, mapper.getTypeFactory().constructCollectionType(List.class, Horario.class));
		result.setHorariosDisponibles(horarios_disponibles);
		
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Consulta disponibilidad Horaria ok.");
		
		logger.debug(EnumEstadosGenerales.OK.getMensaje() +". "+ result.getMensaje() + ". METODO: getTurnosDisponibles.");
		
		return result;
	}
	
	@Transactional( )
	public TurnoResponse reservarTurno(ReservarTurnoRq turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		//VERIFICAMOS LA SSESION
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			System.out.println("session expirada");
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		Turno turnoFinal = new Turno();
		
		//OBTENEMOS EL USUARIO DE SESSION
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		
		switch (usuario.getRole()) {
		case PACIENTE:
			Cliente cliente = (Cliente) usuario;
			//turnoFinal.setCliente(cliente);
			break;
		
		default:
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		//Proceso creacion turno
		// buscamos estado necesario
		Estado estadoReservado = estadoRepository.findByNombre(EnumEstados.RESERVADO.getNombre()).get(0);
		turnoFinal.setEstado(estadoReservado);
		
		turnoFinal.setFechaHoraReserva(new Date(System.currentTimeMillis()));
		//System.out.println("FECHA RESERVAR: " + turnoRequest.getFecha());
		
		logger.debug("FECHA RESERVAR: " + turnoRequest.getFecha());

		//generamos la fecha
        Instant instantFecha = Instant.parse(turnoRequest.getFecha());
        ZonedDateTime zdtFecha = instantFecha.atZone(ZoneId.systemDefault());
        LocalDate fechaLocal = zdtFecha.toLocalDate();

        LocalTime hora = LocalTime.parse(turnoRequest.getHora()); 

        LocalDateTime fechaHoraLocal = LocalDateTime.of(fechaLocal, hora);
        ZonedDateTime zdtFinal = fechaHoraLocal.atZone(ZoneId.systemDefault());

		turnoFinal.setFechaHora( Date.from(zdtFinal.toInstant()));
		
		// buscamos al veterinario/a
		if (turnoRequest.getVeterinariaId() != null && turnoRequest.getVeterinariaId() != 0) {
			turnoFinal.setVeterinaria((veterinariaRepository.findById(turnoRequest.getVeterinariaId()).get()));
		}
		
		if (turnoRequest.getVeterinarioId() != null && turnoRequest.getVeterinarioId() != 0) {
			turnoFinal.setVeterinario((veterinarioRepository.findById(turnoRequest.getVeterinarioId()).get()));
		}
		
		//buscamos al animal
		Animal animal = animalRepository.findById(turnoRequest.getAnimalId()).get();
		if (animal == null ){
			result.setEstado(String.valueOf(EnumEstadosGenerales.ERROR_10002.getCodigo()));
			result.setMensaje(EnumEstadosGenerales.ERROR_10002.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		turnoFinal.setAnimal(animal);
		
		//turnoFinal.setDescripcionPublica(turnoRequest.getDescripcionPublica());
		
		turnoFinal.setEsADomicilio(turnoRequest.isEsDomicilio());

		//se puede guardar el turno??
		// busco estados a tener en cuenta para poder guardar
		List<String> estados = new ArrayList<>();
		estados.add(EnumEstados.RESERVADO.getNombre());
		estados.add(EnumEstados.ACEPTADO.getNombre());
		estados.add(EnumEstados.ATENDIDO.getNombre());
		
		//busco los turnos con los estados previos
		List<Turno> turnosObtenidos = turnoRepository.buscarTurnosPorVeterinariaVeterinarioYFechaYEstado(
				turnoFinal.getVeterinario(),
				turnoFinal.getVeterinaria(),
				turnoFinal.getFechaHora(),
				estados
				);

		// si no devuelve turnos, quiere decir que no hay turnos y que puede guardarse sin problemas
		if(!turnosObtenidos.isEmpty()) {
			result.setEstado(EnumEstadosGenerales.ERROR.getEstado());
			result.setMensaje("Turno ya registrado");
			logger.error(result.getMensaje());
			return result;
		}
		
		//guardamos
		try{
			turnoRepository.save(turnoFinal);
		} catch(Exception e){
			e.printStackTrace();
			result.setEstado(EnumEstadosGenerales.ERROR.getEstado());
			result.setMensaje("Ocurrio un error al guardar el turno");
			logger.error(result.getMensaje());
			return result;
		}
		
		//result.setTurno();
		result.setEstadoReserva(estadoReservado);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Reservar turno ok.");
		
		logger.debug(EnumEstadosGenerales.OK.getMensaje() +". "+ result.getMensaje() + ". METODO: reservarTurno.");
		
		return result;
		//return null;
	}
	
	public TurnoResponse cancelarTurno(AtenderTurnoRq turnoRequest) {
		
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			logger.error(result.getMensaje());
			return result;
		}
		
		//buscamos el estado necesario
		Estado estadoCancelado = estadoRepository.findByNombre(EnumEstados.CANCELADO.getNombre()).get(0);
		
		//hacemos el cambio de estado
		Turno turno = cambiarEstadoTurno(turnoRequest.getIdTurno(), estadoCancelado);

		turno.getVeterinario().setHorarios(null);
		if(turno.getVeterinaria()!=null){
			turno.getVeterinaria().setHorarioAtencion(null);
		}

		result.setTurno(turno);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Cancelar turno ok.");
		
		logger.debug(EnumEstadosGenerales.OK.getMensaje() +". "+ result.getMensaje() + ". METODO: cancelarTurno.");
		
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
	
	public TurnoResponse aceptarTurno(AtenderTurnoRq turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		//CONTROL DE QUE NO SEA UN PACIENTE QUIEN ENVIA LA PETICION
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() == Role.PACIENTE) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Estado estado = estadoRepository.findByNombre(EnumEstados.ACEPTADO.getNombre()).get(0);
		
		Turno turno = cambiarEstadoTurno(turnoRequest.getIdTurno(), estado);

		// turno.getVeterinario().setHorarios(null);
		// if(turno.getVeterinaria()!=null){
		// 	turno.getVeterinaria().setHorarioAtencion(null);
		// }

		result.setTurno(turno);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno ACEPTADO ok.");
		
		
		return result;
	}
	
	public TurnoResponse rechazarTurno(AtenderTurnoRq turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		//CONTROL DE QUE NO SEA UN PACIENTE QUIEN ENVIA LA PETICION
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() == Role.PACIENTE) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Estado estado = estadoRepository.findByNombre(EnumEstados.RECHAZADO.getNombre()).get(0);
		
		Turno turno = cambiarEstadoTurno(turnoRequest.getIdTurno(), estado);

		// turno.getVeterinario().setHorarios(null);
		// if(turno.getVeterinaria()!=null){
		// 	turno.getVeterinaria().setHorarioAtencion(null);
		// }

		result.setTurno(turno);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno RECHAZADO ok.");
		
		
		return result;
	}
	
	public TurnoResponse atenderTurno(AtenderTurnoRq turnoRequest) {
		TurnoResponse result = new TurnoResponse();
		
		PrincipalPawplan session = authenticationFacade.getPrincipal();
		if(session.getLoginDateExpiration()<System.currentTimeMillis()) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
			return result;
		}
		
		//CONTROL DE QUE NO SEA UN PACIENTE O VETERINARIA QUIEN ENVIA LA PETICION
		Usuario usuario = usuarioRepository.findById(session.getClienteId()).get();
		if (usuario.getRole() != Role.VETERINARIO) {
			result.setEstado(String.valueOf(EnumCodigoErrorLogin.LOGIN_2420.getCodigo()));
			result.setMensaje(EnumCodigoErrorLogin.LOGIN_2420.getMensaje());
		}
		
		Turno turno = turnoRepository.findById(turnoRequest.getIdTurno()).get();

		Estado estado = estadoRepository.findByNombre(EnumEstados.ATENDIDO.getNombre()).get(0);
		
		turno.setDescripcionPublica(turnoRequest.getDescripcionPublica());
		turno.setDescripcionPrivada(turnoRequest.getDescripcionPrivada());
		turno.setEstado(estado);

		turnoRepository.save(turno);

		// turno.getVeterinario().setHorarios(null);
		// if(turno.getVeterinaria()!=null){
		// 	turno.getVeterinaria().setHorarioAtencion(null);
		// }

		result.setTurno(turno);
		
		result.setEstado(EnumEstadosGenerales.OK.getEstado());
		result.setMensaje("Turno ATENDIDO ok.");
		
		
		return result;
	}
	
	public Turno cambiarEstadoTurno(Long idTurno, Estado estado) {
		Turno turno = turnoRepository.findById(idTurno).get();
		turno.setEstado(estado);
		return turnoRepository.save(turno);
	}

	public PaginaTurnosRs getMisTurnos (FiltroTurnoRq turnoRequest){

		PaginaTurnosRs rs = new PaginaTurnosRs();

		// System.out.println("NEstado: " + turnoRequest.getNEstado());
		// System.out.println("Fecha recibida: " + turnoRequest.getFecha());
		System.out.println("Cliente ID RQ: " + turnoRequest.getIdCliente());
		System.out.println("Animal ID RQ: " + turnoRequest.getIdAnimal());
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

		String animalesId = null;
		//FILTRO DE ANIMALES SEGUN CLIENTE
		List<Long> animalIds = null;
		if (turnoRequest.getIdAnimal() != null && turnoRequest.getIdAnimal() != 0) {
			//si hay un id especifico entonces buscamos por ese
			animalIds = List.of(turnoRequest.getIdAnimal());
		} else if (turnoRequest.getIdCliente() != null && turnoRequest.getIdCliente() != 0) {
			//si no hay un id especifico de animal y si hay de cliente entonces buscamos todos los animales que pertenecen a este cliente
			animalIds = animalRepository.findIdsByClienteId(turnoRequest.getIdCliente());
		}

		if(animalIds!=null && !animalIds.isEmpty()){
			animalesId = animalIds.stream().map(Object::toString).collect(Collectors.joining(","));  //[1,2] -> "1,2"
		} else{
			animalesId = null;
		}


		//buscar estado que venga en la rq
		Long idEstado = null;
		if(turnoRequest.getNEstado()!=null && !"".trim().equals(turnoRequest.getNEstado())){
			idEstado = estadoRepository.findByNombre(turnoRequest.getNEstado()).get(0).getId();
		}

		
		//fecha sin hora 
		// java.sql.Date fechaSinHora = null;
		// if (turnoRequest.getFecha() != null) {
		// 	fechaSinHora = new java.sql.Date(turnoRequest.getFecha().getTime());
		// }

		LocalDate fechaSinHora = null;
		if (turnoRequest.getFecha() != null) {
			fechaSinHora = turnoRequest.getFecha().toLocalDate();
		}


		
		List<TurnoFb> turnos = null;
		Long total = 0l;

		try {

			System.out.println("idEstado: " + idEstado);
			System.out.println("Fecha recibida: " + turnoRequest.getFecha());
			System.out.println("Cliente ID FIN: " + turnoRequest.getIdCliente());
			System.out.println("Animales FIN: " + animalesId);
			System.out.println("Veterinario ID: " + turnoRequest.getIdVeterinario());


			turnos = turnoRepository.buscarTurnosPagina(
				animalesId, 
				turnoRequest.getIdVeterinaria(), 
				turnoRequest.getIdVeterinario(), 
				idEstado, 
				fechaSinHora, 
				turnoRequest.getPage(), turnoRequest.getSize(), turnoRequest.getOrderBy(), turnoRequest.getOrderDir());
    		total = turnoRepository.contarTurnos(
				animalesId, 
				turnoRequest.getIdVeterinaria(), 
				turnoRequest.getIdVeterinario(), 
				idEstado, 
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

	public ClientesDeVeterinarieRs obtenerClientesConAnimales() throws JsonProcessingException {

		ClientesDeVeterinarieRs rs = new ClientesDeVeterinarieRs();
		rs.setEstado("ERROR");
		rs.setClientes(null);

		//Us en session
		PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();

		if(Role.PACIENTE.name().equals(principalPawplan.getRole().name())){
			rs.setMensaje("Usted no tiene permiso para acceder a esta funcionalidad");
			return rs;
		}

		List<ClienteDTO> clientes = new ArrayList<>();
		try{
			System.out.println("ROL: " + principalPawplan.getRole().name() + " ID: "+  principalPawplan.getClienteId());
			//																					        VETERINARIO - VETERINARIA            Id VETERINARIE
			List<Map<String, Object>> resultados = turnoRepository.findClientesConAnimalesByFiltro(principalPawplan.getRole().name(), principalPawplan.getClienteId());

			ObjectMapper mapper = Constantes.getObjectMapper();

			for (Map<String, Object> fila : resultados) {
				ClienteDTO cliente = new ClienteDTO();
				cliente.setClienteId(Long.parseLong(fila.get("cliente_id").toString()));
				cliente.setClienteNombre(fila.get("cliente_nombre").toString());
				cliente.setClienteApellido(fila.get("cliente_apellido").toString());
				cliente.setDni(fila.get("dni").toString());

				String animalesJson = fila.get("animales").toString();
				List<AnimalDTO> animales = mapper.readValue(animalesJson,
						mapper.getTypeFactory().constructCollectionType(List.class, AnimalDTO.class));

				cliente.setAnimales(animales);

				clientes.add(cliente);
			}
		} catch (Exception e){
			e.printStackTrace();
			rs.setMensaje("Ocurrio un error al buscar los clientes");
			return rs;
		}

        rs.setClientes(clientes);
		rs.setEstado("OK");
		return rs;
    }

}
