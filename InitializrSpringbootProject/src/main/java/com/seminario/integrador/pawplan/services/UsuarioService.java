package com.seminario.integrador.pawplan.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.UsuarioRequest;
import com.seminario.integrador.pawplan.controller.values.UsuarioResponse;
import com.seminario.integrador.pawplan.exception.PawPlanRuleException;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.ClienteRepository;
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

	public UsuarioResponse<ArrayList<?>> consultar() {
		UsuarioResponse<ArrayList<?>> consulta = new UsuarioResponse<ArrayList<?>>();

		ArrayList<?> usuarios = (ArrayList<?>) usuarioRepository.findAll();

		consulta.setUsuario(usuarios);
		consulta.setEstado("OK");

		return consulta;
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
		cliente.setAnimales(usuarioRequest.getAnimales());
		cliente.setTelefono(usuarioRequest.getTelefono());
		cliente.setCorreo(usuarioRequest.getCorreo());
		cliente.setContrasenia(sessionManager.hashPassword(usuarioRequest.getContrasenia()));

		clienteRepository.save(cliente);
		
		return cliente;
	}

	private Veterinaria crearModificarVeterinaria(Veterinaria veterinaria, UsuarioRequest usuarioRequest) {

		veterinaria.setTelefono(usuarioRequest.getTelefono());
		veterinaria.setCorreo(usuarioRequest.getCorreo());
		veterinaria.setContrasenia(sessionManager.hashPassword(usuarioRequest.getContrasenia()));

		veterinaria.setCuit(usuarioRequest.getCuit());
		veterinaria.setAptoCirugia(usuarioRequest.isAptoCirugia());
		veterinaria.setHorarioAtencion(usuarioRequest.getHorarioAtencion());
		veterinaria.setVeterinarios(usuarioRequest.getVeterinarios());
		veterinaria.setHaceDomicilio(usuarioRequest.isHaceDomicilio());
		
		veterinariaRepository.save(veterinaria);
		
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
		veterinario.setHaceGuardia(usuarioRequest.isHaceGuardia());
		veterinario.setHorario(usuarioRequest.getHorario());
		veterinario.setEspecialidad(usuarioRequest.getEspecialidad());
		veterinario.setHaceDomicilio(usuarioRequest.isHaceDomicilio());
		
		veterinarioRepository.save(veterinario);
		
		return veterinario;
	}
        
        
        public ArrayList<Domicilio> getDomiciliosUsCliente(String dni){
            return this.clienteRepository.findDomiciliosByCliente(dni);
        }
	
	
}
