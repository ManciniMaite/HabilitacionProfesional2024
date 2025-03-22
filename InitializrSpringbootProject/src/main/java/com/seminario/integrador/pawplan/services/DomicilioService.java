package com.seminario.integrador.pawplan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.DomicilioRq;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.model.Ciudad;
import com.seminario.integrador.pawplan.model.Domicilio;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.repository.CiudadRepository;
import com.seminario.integrador.pawplan.repository.DomicilioRepository;

@Service
public class DomicilioService {
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Lazy
    @Autowired
    private UsuarioService usuarioService;

    public List<Domicilio> getByUsuario(String us){
        List<Domicilio> domiciliosList;

        Optional<Usuario> usuario = usuarioService.consultarUsuario(us);
        if(usuario.isPresent()){
            domiciliosList = this.domicilioRepository.findByUsuarioId(usuario.get().getId());
        } else{
            throw new UnsupportedOperationException("Usuario no encontrado: " + us);
        }

        return domiciliosList;
    }


    public Domicilio nuevDomicilio(DomicilioRq domicilioDTO){
        Domicilio domicilio = new Domicilio();

        
        if(domicilioDTO.getCiudadId()==null){
            throw new UnsupportedOperationException("Id de ciudad null");
        } else{
            Optional<Ciudad> ciudadOptional = ciudadRepository.findById(domicilioDTO.getCiudadId());

            if (ciudadOptional.isPresent()){
                domicilio.setCiudad(ciudadOptional.get());
            } else {
                throw new UnsupportedOperationException("Ciudad no encontrada con el ID: " + domicilioDTO.getCiudadId());
            }
        }
        
        if(domicilioDTO.getUsuario()==null){
            throw new UnsupportedOperationException("usuario rq null");
        } else{
            Optional<Usuario> usuario = usuarioService.consultarUsuario(domicilioDTO.getUsuario());
            if(usuario.isPresent()){
                domicilio.setUsuario(usuario.get());
            } else{
                throw new UnsupportedOperationException("Usuario no encontrado: " + domicilioDTO.getUsuario());
            }
        }
        

        domicilio.setCalle(domicilioDTO.getCalle());
        domicilio.setNumero(domicilioDTO.getNumero());
        domicilio.setObservaciones(domicilioDTO.getObservaciones());

        Domicilio domicilioNuevo = this.domicilioRepository.save(domicilio);

        return domicilioNuevo;
    }

    public Domicilio actualizarDomicilio(DomicilioRq domicilioDTO){
        Domicilio domicilioExistente;

        try{
            Optional<Domicilio> domicilio =  this.domicilioRepository.findById(domicilioDTO.getIdDomicilio());
    
            if(domicilio.isPresent()){
                domicilioExistente = domicilio.get();
            } else{
                throw new UnsupportedOperationException("Domicilio no encontrado");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Falló la busqueda del domicilio");
        }

        try{
            Optional<Usuario> usuario = usuarioService.consultarUsuario(domicilioDTO.getUsuario());
            if(usuario.isPresent()){
                if(usuario.get().getId() != domicilioExistente.getUsuario().getId()){
                    throw new UnsupportedOperationException("El usuario no tiene permiso para modificar el domicilio");
                }
            } else{
                throw new UnsupportedOperationException("Usuario no encontrado: " + domicilioDTO.getUsuario());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Falló la busqueda del Usuario");
        }

        domicilioExistente.setCalle(domicilioDTO.getCalle());
        domicilioExistente.setNumero(domicilioDTO.getNumero());
        domicilioExistente.setObservaciones(domicilioDTO.getObservaciones());

        return this.domicilioRepository.save(domicilioExistente);

    }

    public Response deleteDomicilio(DomicilioRq domicilioDTO){
        Response rs = new Response();
        Domicilio domicilioExistente;

        try{
            Optional<Domicilio> domicilio =  this.domicilioRepository.findById(domicilioDTO.getIdDomicilio());
    
            if(domicilio.isPresent()){
                domicilioExistente = domicilio.get();
            } else{
                rs.setEstado("ERROR");
                rs.setMensaje("Domicilio no encontrado");
                return rs;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Falló la busqueda del domicilio");
        }

        try{
            Optional<Usuario> usuario = usuarioService.consultarUsuario(domicilioDTO.getUsuario());
            if(usuario.isPresent()){
                if(usuario.get().getId() != domicilioExistente.getUsuario().getId()){
                    throw new UnsupportedOperationException("El usuario no tiene permiso para eliminar el domicilio");
                }
            } else{
                rs.setEstado("ERROR");
                rs.setMensaje("Usuario no encontrado");
                return rs;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Falló la busqueda del Usuario");
        }

        try{
            this.domicilioRepository.deleteById(domicilioExistente.getId());
            Optional<Domicilio> domicilio =  this.domicilioRepository.findById(domicilioDTO.getIdDomicilio());
            if(domicilio.isPresent()){
                rs.setEstado("ERROR");
                rs.setMensaje("No se elimino el Domicilio");
                return rs;
            } else{
                rs.setEstado("OK");
                rs.setMensaje("Domicilio eliminado!");
                return rs;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Falló la busqueda del Usuario");
        }
    }
}
