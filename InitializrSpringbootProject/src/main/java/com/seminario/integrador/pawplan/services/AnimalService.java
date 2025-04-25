/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.seminario.integrador.pawplan.services;

import com.seminario.integrador.pawplan.controller.values.AnimalRq;
import com.seminario.integrador.pawplan.controller.values.AnimalRs;
import com.seminario.integrador.pawplan.controller.values.ListaAnimalesRs;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Raza;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.repository.AnimalRepository;
import com.seminario.integrador.pawplan.repository.RazaRepository;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author maite
 */
@Service
public class AnimalService {
	@Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private AnimalRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RazaRepository razaRepository;
    
    public ListaAnimalesRs findByCliente(String usuario){
    	
    	//PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
    	
        ListaAnimalesRs rs = new ListaAnimalesRs();
        rs.setEstado("OK");
        rs.setMensaje("");
        
        List<Animal> animales =  new ArrayList<>();

        Long usuarioId;

        try{
            Optional<Usuario> opUs = this.usuarioRepository.findByDniOrCuit(usuario);
            
            if(!opUs.isPresent()){
                rs.setEstado("ERROR");
                rs.setMensaje("Usuario no encontrado");
                return rs;
            } else {
                usuarioId = opUs.get().getId();

            }
            
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al buscar el usuario");
            return rs;
        }
        
        try{
            // animales = this.repository.findByCliente_Id(principalPawplan.getClienteId());

            animales = this.repository.findByCliente_IdAndEsActivoTrue(usuarioId);
            
            if(animales == null){
                rs.setEstado("ERROR");
                rs.setMensaje("Ocurrio un error al obtener los animales - 02");
                return rs;
            }
            
            rs.setAnimales(animales);
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al obtener los animales - 01");
            return rs;
        }
               
        
        return rs;
                
    }
    
    public AnimalRs crearAnimal(AnimalRq rq){
        AnimalRs rs = new AnimalRs();
        rs.setEstado("OK");
        try {
            Animal nuevoAnimal = new Animal();
            nuevoAnimal.setNombre(rq.getNombre());
            nuevoAnimal.setFechaNac(rq.getFechaNac());
            nuevoAnimal.setPeso(rq.getPeso());
            nuevoAnimal.setEsActivo(true);
            
            // Buscar la raza por id
            Optional<Raza> razaOpt = razaRepository.findById(rq.getRazaId());
            if(razaOpt.isPresent()) {
                nuevoAnimal.setRaza(razaOpt.get());
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("Raza no encontrada con ID: " + rq.getRazaId());
                return rs;
            }
            
            // Buscar el cliente por id
            Long idUsuario = 0l;
            if(rq.getUsuarioId()!=null && rq.getUsuarioId()!= 0){
                idUsuario=rq.getUsuarioId();
            } else{
                PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
                idUsuario=principalPawplan.getClienteId();
            }
            Optional<Usuario> usOptional = usuarioRepository.findById(idUsuario);
            if(usOptional.isPresent()) {
                nuevoAnimal.setCliente((Cliente) usOptional.get());
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("Cliente no encontrado con ID: " + rq.getUsuarioId());
                return rs;
            }

            nuevoAnimal = repository.save(nuevoAnimal);

            rs.setAnimal(nuevoAnimal);

        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al guardar el animal - 01");
            return rs;
        }
        return rs;
    }
    
    public AnimalRs updateAnimal(AnimalRq rq) {
        System.out.println(rq);
        AnimalRs rs = new AnimalRs();
        rs.setEstado("OK");
        try {
            Optional<Animal> animalExistente = repository.findById(rq.getId());
            if (animalExistente.isEmpty()) {
                rs.setEstado("ERROR");
                rs.setMensaje("No fue posible recuperar el animal con ID: " + rq.getId());
                return rs;
            }

            Animal animal = animalExistente.get();
            animal.setNombre(rq.getNombre());
            animal.setPeso(rq.getPeso());
            animal.setFechaNac(rq.getFechaNac());

            Animal animalActualizado = repository.save(animal);
            rs.setAnimal(animalActualizado);

        } catch (Exception e) {
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrió un error al actualizar el animal");
        }
        return rs;
    }

    public Response delete(Long id) {
        Response rs = new Response();
        try {
            Optional<Animal> animalExistente = repository.findById(id);
            if (animalExistente.isEmpty()) {
                rs.setEstado("ERROR");
                rs.setMensaje("No fue posible recuperar el animal con ID: " + id);
                return rs;
            }
            
            animalExistente.get().setEsActivo(false);
            repository.save(animalExistente.get());
            //repository.deleteById(id);
    
            rs.setEstado("OK");
            rs.setMensaje("Registro eliminado con éxito");
    
        } catch (Exception e) {
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrió un error al eliminar el animal");
        }
        return rs;
    }
    
}