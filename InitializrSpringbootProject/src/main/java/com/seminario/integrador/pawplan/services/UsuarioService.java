package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.AnimalRq;
import com.seminario.integrador.pawplan.controller.values.UsuarioRequest;
import com.seminario.integrador.pawplan.controller.values.UsuarioResponse;
import com.seminario.integrador.pawplan.exception.PawPlanRuleException;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.DiaHorarioAtencion;
import com.seminario.integrador.pawplan.model.Horario;
import com.seminario.integrador.pawplan.model.TipoEspecie;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.ClienteRepository;
import com.seminario.integrador.pawplan.repository.DiaHorarioAtencionRepository;
import com.seminario.integrador.pawplan.repository.TipoEspecieRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.repository.VeterinariaRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.Role;
import com.seminario.integrador.pawplan.security.SessionManager;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class UsuarioService {

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	@Autowired
	private VeterinarioRepository veterinarioRepository;
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;

	@Lazy
	@Autowired
	private DomicilioService domicilioService;

	@Autowired
	private AnimalService animalService;

	@Autowired 
	private TipoEspecieRepository tipoEspecieRepository;

	@Autowired
	private DiaHorarioAtencionRepository diaHorarioAtencionRepository;

	// @Autowired
	// private DomicilioService domicilioService;

	public UsuarioResponse<ArrayList<?>> consultar() {
		UsuarioResponse<ArrayList<?>> consulta = new UsuarioResponse<ArrayList<?>>();

		ArrayList<?> usuarios = (ArrayList<?>) usuarioRepository.findAll();

		consulta.setUsuario(usuarios);
		consulta.setEstado("OK");

		return consulta;
	}

	public Optional<Usuario> consultarUsuario(String us) {
		Optional<Usuario> usuario = usuarioRepository.findByDniOrCuit(us);
		return usuario;
	}

	public UsuarioResponse<?> CrearUsuario(UsuarioRequest usuarioRequest) {

		UsuarioResponse<Usuario> rs = new UsuarioResponse<Usuario>();

		switch (usuarioRequest.getTipoUsuario()) {
		case PACIENTE:
			Cliente cliente = new Cliente();

			cliente.setRole(Role.PACIENTE);

			cliente = crearModificarCliente(cliente, usuarioRequest);

			rs.setUsuario(cliente);

			break;
			
		case VETERINARIA:

			Veterinaria veterinaria = new Veterinaria();

			veterinaria.setRole(Role.VETERINARIA);

			veterinaria = crearModificarVeterinaria(veterinaria, usuarioRequest);
			
			rs.setUsuario(veterinaria);

			break;
			
		case VETERINARIO:
			Veterinario veterinario = new Veterinario();

			veterinario.setRole(Role.VETERINARIO);

			veterinario = crearModificarVeterinario(veterinario, usuarioRequest);
			
			rs.setUsuario(veterinario);
			
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + usuarioRequest.getTipoUsuario());
		}

		
		rs.setEstado("OK");
		
		return rs;
	}

	public UsuarioResponse<?> modificarUsuario(UsuarioRequest usuarioRequest) throws PawPlanRuleException {
		
		PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
		
		UsuarioResponse<Usuario> consulta = new UsuarioResponse<>();

		Usuario usuario = usuarioRepository.findByCorreo(usuarioRequest.getCorreo());

		if(principalPawplan.getClienteId()!=usuario.getId()) {
			throw new PawPlanRuleException(0,"EL USUARIO NO COINCIDE");
		}
		if (usuario == null) {
			throw new PawPlanRuleException(0,"USUARIO NULL");
		}
			
		switch (usuario.getRole()) {
		case PACIENTE:
			Cliente cliente = (Cliente) usuario;
			usuario = crearModificarCliente(cliente, usuarioRequest);
			break;
		case VETERINARIA:
			Veterinaria veterinaria = (Veterinaria) usuario;
			usuario = crearModificarVeterinaria(veterinaria, usuarioRequest);
			break;
		case VETERINARIO:
			Veterinario veterinario = (Veterinario) usuario;
			usuario = crearModificarVeterinario(veterinario, usuarioRequest);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + usuario.getRole());
		}
		
		consulta.setUsuario(usuario);
		consulta.setEstado("OK");

		return consulta;
	}

	
	public UsuarioResponse<?> eliminarUsuario(UsuarioRequest usuarioRequest) throws PawPlanRuleException {
		UsuarioResponse<Usuario> consulta = new UsuarioResponse<>();

		Usuario usuario = usuarioRepository.findByCorreo(usuarioRequest.getCorreo());

		if (usuario == null) {
			throw new PawPlanRuleException(0,"USUARIO NULL");
		}
			
		usuario.setActivo(false);
		usuarioRepository.save(usuario);
		
		consulta.setUsuario(usuario);
		consulta.setEstado("OK");

		return consulta;
	}
	
	
	
	
	private Cliente crearModificarCliente(Cliente cliente, UsuarioRequest usuarioRequest) {
		
		cliente.setApellido(usuarioRequest.getApellido());
		cliente.setNombre(usuarioRequest.getNombre());
		cliente.setDni(usuarioRequest.getDni());
		//cliente.setAnimales(usuarioRequest.getAnimales());
		cliente.setTelefono(usuarioRequest.getTelefono());
		cliente.setCorreo(usuarioRequest.getCorreo());
		cliente.setContrasenia(sessionManager.hashPassword(usuarioRequest.getContrasenia()));

		clienteRepository.save(cliente);

		if(!usuarioRequest.getAnimales().isEmpty()){
			for(AnimalRq a : usuarioRequest.getAnimales()){
				a.setUsuarioId(cliente.getId());
				this.animalService.crearAnimal(a);
			}
		}

		if(usuarioRequest.getDomicilio() != null){
			this.domicilioService.nuevDomicilio(usuarioRequest.getDomicilio());
		}

		return cliente;
	}

	private Veterinaria crearModificarVeterinaria(Veterinaria veterinaria, UsuarioRequest usuarioRequest) {

		veterinaria.setTelefono(usuarioRequest.getTelefono());
		veterinaria.setCorreo(usuarioRequest.getCorreo());
		veterinaria.setRazonSocial(usuarioRequest.getRazonSocial());
		veterinaria.setContrasenia(sessionManager.hashPassword(usuarioRequest.getContrasenia()));

		veterinaria.setCuit(usuarioRequest.getCuit());
		veterinaria.setAptoCirugia(usuarioRequest.isAptoCirugia());
		veterinaria.setHorarioAtencion(usuarioRequest.getHorario());
		veterinaria.setVeterinarios(usuarioRequest.getVeterinarios());
		
		if(usuarioRequest.isLocalFisico()){
			veterinaria.setHaceDomicilio(false);
		} else{
			veterinaria.setHaceDomicilio(true);
		}

		veterinariaRepository.save(veterinaria);

		if(usuarioRequest.getDomicilio() != null){
			this.domicilioService.nuevDomicilio(usuarioRequest.getDomicilio());
		}

		if(!usuarioRequest.getHorario().isEmpty()){
			List<DiaHorarioAtencion> diasHorarios = new ArrayList<>();
			for (DiaHorarioAtencion diaReq : usuarioRequest.getHorario()) {
				DiaHorarioAtencion diaHorario = new DiaHorarioAtencion();
				diaHorario.setDia(diaReq.getDia());
				diaHorario.setIdUsuario(veterinaria.getId());

				List<Horario> horarios = new ArrayList<>();
				for (Horario hReq : diaReq.getHorarios()) {
					Horario horario = new Horario();
					horario.setHoraInicio(hReq.getHoraInicio());
					horario.setHoraFin(hReq.getHoraFin());
					horario.setDiaHorarioAtencion(diaHorario); // ASOCIACIÓN INVERSA
					horarios.add(horario);
				}
				diaHorario.setHorarios(horarios);
				diasHorarios.add(diaHorario);
			}

			diaHorarioAtencionRepository.saveAll(diasHorarios);
		}
		
		
		return veterinaria;
	}

	private Veterinario crearModificarVeterinario(Veterinario veterinario, UsuarioRequest usuarioRequest) {

		veterinario.setApellido(usuarioRequest.getApellido());
		veterinario.setNombre(usuarioRequest.getNombre());
		veterinario.setDni(usuarioRequest.getDni());

		veterinario.setTelefono(usuarioRequest.getTelefono());
		veterinario.setCorreo(usuarioRequest.getCorreo());
		veterinario.setContrasenia(sessionManager.hashPassword(usuarioRequest.getContrasenia()));

		veterinario.setFechaNac(usuarioRequest.getFechaNac());
		veterinario.setMatricula(usuarioRequest.getMatricula());
		veterinario.setEsIndependiente(usuarioRequest.isEsIndependiente());
		if(veterinario.isEsIndependiente()){ //si trabaja de forma independiente solo trabaja atendiendo a domicilio
			veterinario.setHaceDomicilio(true);
			veterinario.setHaceGuardia(usuarioRequest.isHaceGuardia());
		} else{
			veterinario.setHaceDomicilio(false);
			veterinario.setHaceGuardia(false);
		}


		ArrayList<TipoEspecie> alte = new ArrayList<TipoEspecie>();
		for(Long id : usuarioRequest.getTipoEspeciesIds()){
			Optional<TipoEspecie> te = this.tipoEspecieRepository.findById(id);
			if(te.isPresent()){
				alte.add(te.get());
			} else{
				throw new IllegalArgumentException("Unexpected value of tipo_especie id: " + id);
			}
		}
		veterinario.setTiposEspecie(alte);

		veterinarioRepository.save(veterinario);


		if( veterinario.isEsIndependiente() && !usuarioRequest.getHorario().isEmpty()){
			List<DiaHorarioAtencion> diasHorarios = new ArrayList<>();
			for (DiaHorarioAtencion diaReq : usuarioRequest.getHorario()) {
				DiaHorarioAtencion diaHorario = new DiaHorarioAtencion();
				diaHorario.setDia(diaReq.getDia());
				diaHorario.setIdUsuario(veterinario.getId());
	
				List<Horario> horarios = new ArrayList<>();
				for (Horario hReq : diaReq.getHorarios()) {
					Horario horario = new Horario();
					horario.setHoraInicio(hReq.getHoraInicio());
					horario.setHoraFin(hReq.getHoraFin());
					horario.setDiaHorarioAtencion(diaHorario); // ASOCIACIÓN INVERSA
					horarios.add(horario);
				}
				diaHorario.setHorarios(horarios);
				diasHorarios.add(diaHorario);
			}
	
			diaHorarioAtencionRepository.saveAll(diasHorarios);

			 //agregar un domicilio para saber en que ciudad atiende
			if(usuarioRequest.getDomicilio() != null){
				this.domicilioService.nuevDomicilio(usuarioRequest.getDomicilio());
			}
		}

		return veterinario;
	}
        
		
        
	// public List<Domicilio> getDomiciliosUsCliente(String dni){
	// 	return this.domicilioService.getByUsuario(dni);
	// }
	
	
}
