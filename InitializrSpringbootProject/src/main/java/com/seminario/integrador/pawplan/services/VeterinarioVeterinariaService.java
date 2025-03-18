package com.seminario.integrador.pawplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.VeterinarioVeterinariaResponse;
import com.seminario.integrador.pawplan.model.Ciudad;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.repository.VeterinariaRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VeterinarioVeterinariaService {
    
    @Autowired
    VeterinariaRepository veterinariaRepository;
    
    @Autowired
    VeterinarioRepository veterinarioRepository;
    
    @Autowired
    CiudadService serviceCiudad;
	
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
        
        public VeterinarioVeterinariaResponse getByCiudad(Long id){
            VeterinarioVeterinariaResponse rs = new VeterinarioVeterinariaResponse();
            ArrayList<Veterinario> veterinarios = new ArrayList<Veterinario>();
            ArrayList<Veterinaria> veterinarias = new ArrayList<Veterinaria>();
            try {
                Optional<Ciudad> ciudadExistente;
                ciudadExistente = this.serviceCiudad.findById(id);
                if(ciudadExistente.isPresent()){
                    try{
                        veterinarios = this.veterinarioRepository.findByCiudad(id);
                    } catch (Exception e){
                        e.printStackTrace();
                        rs.setMensaje("Ocurrio un error al recuperar los veterinarios");
                        rs.setEstado("ERROR");
                        return rs;
                    }
                    rs.setVeterinariosIndependientes(veterinarios);
                    try{
                        veterinarias = this.veterinariaRepository.findByCiudad(id);
                    } catch (Exception e){
                        e.printStackTrace();
                        rs.setMensaje("Ocurrio un error al recuperar las veterinarias");
                        rs.setEstado("ERROR");
                        return rs;
                    }
                    rs.setVeterinarias(veterinarias);
                } else {
                    rs.setMensaje("No fue posible recuperar la ciudad indicada");
                    rs.setEstado("ERROR");
                    return rs;
                }
            } catch (Exception e){
                e.printStackTrace();
                rs.setMensaje("Ocurrio un error al recuperar la ciudad");
                rs.setEstado("ERROR");
                return rs;
            }
            rs.setEstado("OK");
            return rs;
        }
}
