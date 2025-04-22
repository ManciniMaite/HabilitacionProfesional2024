package com.seminario.integrador.pawplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.ProfesionalPorVeterinaria;
import com.seminario.integrador.pawplan.controller.values.ProfesionalesPorVeterinariaRs;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.controller.values.TurnoRequest;
import com.seminario.integrador.pawplan.controller.values.VeterinariaXCiudad;
import com.seminario.integrador.pawplan.controller.values.VeterinariaXCiudadImpl;
import com.seminario.integrador.pawplan.controller.values.VeterinarioVeterinariaResponse;
import com.seminario.integrador.pawplan.controller.values.VeterinarioXciudad;
import com.seminario.integrador.pawplan.model.Ciudad;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.TurnoRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.repository.VeterinariaRepository;
import com.seminario.integrador.pawplan.repository.VeterinarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.Role;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioVeterinariaService {
    
	private Logger logger = LoggerFactory.getLogger(VeterinarioVeterinariaService.class);
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
        
    public VeterinarioVeterinariaResponse getByCiudad(Long idCiudad, Long idTipoEspecie, boolean domicilio){
    VeterinarioVeterinariaResponse rs = new VeterinarioVeterinariaResponse();
    ArrayList<VeterinarioXciudad> veterinariosIndependientes = new ArrayList<>();
    ArrayList<VeterinariaXCiudadImpl> veterinariasConVeterinarios = new ArrayList<>();

    try {
        Optional<Ciudad> ciudadExistente = this.serviceCiudad.findById(idCiudad);
        if (ciudadExistente.isEmpty()) {
            rs.setMensaje("No fue posible recuperar la ciudad indicada");
            rs.setEstado("ERROR");
            return rs;
        }

        try {
            veterinariosIndependientes = this.veterinarioRepository.findByCiudad(idCiudad, idTipoEspecie, domicilio);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setMensaje("Ocurrió un error al recuperar los veterinarios");
            rs.setEstado("ERROR");
            return rs;
        }
        rs.setVeterinariosIndependientes(veterinariosIndependientes);

        try {
            ArrayList<VeterinariaXCiudad> veterinarias = this.veterinariaRepository.findByCiudad(idCiudad, domicilio);

            for (VeterinariaXCiudad veterinaria : veterinarias) {
                List<VeterinarioXciudad> veterinariosDeVeterinaria = this.veterinarioRepository.findByVeterinariaAndTipoEspecie(veterinaria.getId(), idTipoEspecie);

                VeterinariaXCiudadImpl veterinariaConVeterinarios = new VeterinariaXCiudadImpl(veterinaria.getId(), veterinaria.getRazonSocial());
                veterinariaConVeterinarios.setVeterinarios(veterinariosDeVeterinaria);

                veterinariasConVeterinarios.add(veterinariaConVeterinarios);
            }

        } catch (Exception e) {
            e.printStackTrace();
            rs.setMensaje("Ocurrió un error al recuperar las veterinarias");
            rs.setEstado("ERROR");
            return rs;
        }

        rs.setVeterinarias(veterinariasConVeterinarios);

    } catch (Exception e) {
        e.printStackTrace();
        rs.setMensaje("Ocurrió un error al recuperar la ciudad");
        rs.setEstado("ERROR");
        return rs;
    }

    rs.setEstado("OK");
    return rs;
}


    public ProfesionalesPorVeterinariaRs getProfesionales(){
        ProfesionalesPorVeterinariaRs rs = new ProfesionalesPorVeterinariaRs();

        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
        
        if(!Role.VETERINARIA.equals(principalPawplan.getRole())){
            rs.setEstado("ERROR");
            rs.setMensaje("Usted no tiene permisos para acceder a esta funcionalidad");
            return rs;
        } 

        List<ProfesionalPorVeterinaria> veterinarios = new ArrayList<>();
        try{
            veterinarios = this.veterinarioRepository.findByVeterinariaId(principalPawplan.getClienteId());
        }catch(Exception e ){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener los profesionales");
        }

        rs.setEstado("OK");
        rs.setMensaje("");
        rs.setProfesionales(veterinarios);

        return rs;
    }


    public Response addVeterinarioAVeterinaria(Long idProfesional){
        Response rs = new Response();

        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
        if(!Role.VETERINARIA.equals(principalPawplan.getRole())){
            rs.setEstado("ERROR");
            rs.setMensaje("Usted no tiene permisos para acceder a esta funcionalidad");
            return rs;
        }

        Optional<Veterinaria> veterinaria = Optional.empty();
        try{
            veterinaria = this.veterinariaRepository.findById(principalPawplan.getClienteId());
            if(!veterinaria.isPresent()){
                rs.setEstado("ERROR");
                rs.setMensaje("No se encontraron los datos de la veterinaria");
                return rs;
            }
        }catch(Exception e ){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener los datos de la Veterinaria");
        }

        try{
            Optional<Veterinario> opVet = this.veterinarioRepository.findById(idProfesional);

            if(opVet.isPresent()){
                opVet.get().setVeterinaria(veterinaria.get());
                this.veterinarioRepository.save(opVet.get());
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("No se encontró el profesional");
                return rs;
            }
        }catch(Exception e ){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener el Prfesional");
        }

        rs.setEstado("OK");
        rs.setMensaje("");
        return rs;
    }

    public Response quitarVeterinarioDeVeterinaria(Long idProfesional){
        Response rs = new Response();

        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
        if(!Role.VETERINARIA.equals(principalPawplan.getRole())){
            rs.setEstado("ERROR");
            rs.setMensaje("Usted no tiene permisos para acceder a esta funcionalidad");
            return rs;
        }

        Optional<Veterinaria> veterinaria = Optional.empty();
        try{
            veterinaria = this.veterinariaRepository.findById(principalPawplan.getClienteId());
            if(!veterinaria.isPresent()){
                rs.setEstado("ERROR");
                rs.setMensaje("No se encontraron los datos de la veterinaria");
                return rs;
            }
        }catch(Exception e ){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener los datos de la Veterinaria");
        }

        try{
            Optional<Veterinario> opVet = this.veterinarioRepository.findById(idProfesional);

            if(opVet.isPresent()){
                if(opVet.get().getVeterinaria().getId() == veterinaria.get().getId()) {
                    opVet.get().setVeterinaria(null);
                    this.veterinarioRepository.save(opVet.get());
                } else {
                    rs.setEstado("ERROR");
                    rs.setMensaje("El veterinario no pertenece a la veterinaria en sesion");
                    return rs;
                }
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("No se encontró el profesional");
                return rs;
            }
        }catch(Exception e ){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener el Prfesional");
        }

        rs.setEstado("OK");
        rs.setMensaje("");
        return rs;
    }

    public Usuario getByDni(String dni){
        Optional<Usuario> us = this.usuarioRepository.findByDniOrCuit(dni); 

        return us.get();
    }

}
