/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.seminario.integrador.pawplan.services;

import com.seminario.integrador.pawplan.controller.values.AnimalRs;
import com.seminario.integrador.pawplan.controller.values.ListaAnimalesRs;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.repository.AnimalRepository;
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
    private AnimalRepository repository;
    
    public ListaAnimalesRs findByCliente(Long idCliente){
        ListaAnimalesRs rs = new ListaAnimalesRs();
        rs.setEstado("OK");
        rs.setMensaje("");
        
        List<Animal> animales =  new ArrayList<>();
        
        try{
            animales = this.repository.findByIdCliente(idCliente);
            
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
    
    public AnimalRs crearAnimal(Animal rq){
        AnimalRs rs = new AnimalRs();
        rs.setEstado("OK");
        try{
            Animal nuevoAnimal = this.repository.save(rq);
            
            if(nuevoAnimal == null){
                rs.setEstado("ERROR");
                rs.setMensaje("Ocurrio un error al guardar el animal - 02");
                return rs;
            }
            
            rs.setAnimal(nuevoAnimal);
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al guardar el animal - 01");
            return rs;
        }
        return rs;
    }
    
    public AnimalRs UpdateAnimal(Animal rq){
        AnimalRs rs = new AnimalRs();
        rs.setEstado("OK");
        try{
            Optional<Animal> animalExistente = this.repository.findById(rq.getId());
            
            if(animalExistente.isPresent()){
                Animal animal = animalExistente.get();
                animal.setFoto(rq.getFoto());
                animal.setNombre(rq.getNombre());
                animal.setPeso(rq.getPeso());
                animal.setFechaNac(rq.getFechaNac());
                
                Animal animalActualizado =  this.repository.save(animal);
                
                if(animalActualizado == null){
                    rs.setEstado("ERROR");
                    rs.setMensaje("Ocurrio un error al actualizar el animal");
                    return rs;
                }
                
                rs.setAnimal(animalActualizado);
                
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("No fue posible recuperar el animal - 02");
                return rs;
            }
            
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("Ocurrio un error al guardar el animal - 01");
            return rs;
        }
        return rs;
    }
    
    public Response delete(Long id){
        Response rs = new Response();
        
        try{
            Optional<Animal> animalExistente = this.repository.findById(id);
            
            if(animalExistente.isPresent()){
                Animal animal = animalExistente.get();
                animal.setEsActivo(false);
                
                Animal animalActualizado =  this.repository.save(animal);
                
                if(animalActualizado == null){
                    rs.setEstado("ERROR");
                    rs.setMensaje("Ocurrio un error al eliminar el animal");
                    return rs;
                }
                
                
            } else {
                rs.setEstado("ERROR");
                rs.setMensaje("No fue posible recuperar el animal - 02");
                return rs;
            }
            
        } catch (Exception e){
            e.printStackTrace();
            rs.setEstado("ERROR");
            rs.setMensaje("No fue posible recuperar el animal - 01");
            return rs;
        }
        
        rs.setEstado("OK");
        rs.setMensaje("Registro eliminado con exito");
        return rs;
    }
    
}
