/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.controller.values.SessionManagerResponse;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.exception.PawPlanRuleException;
import com.seminario.integrador.pawplan.model.Cliente;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;

import java.security.GeneralSecurityException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sebastian
 */
@Service
@Transactional
public class SessionManager {

    private Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Hasher hasher;

    @Autowired
    private EncriptadorToken encriptador;

    private ObjectMapper mapper = Constantes.getObjectMapper();

    /**
     * Obtener el token de autenticacion
     *
     * @param userName
     * @param password
     *
     * @return
     * @throws Exception
     *
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public SessionManagerResponse login(String correo, String password) throws PawPlanRuleException {
        
        SessionManagerResponse rs = new SessionManagerResponse();

        //obtener los datos del usuario desde la base de datos. 
        Usuario user = usuarioRepository.findByCorreo(correo);

        //verificar si el usuario existe
        if (user == null) {
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2400);
        }

        //verificar si el usuario esta habilitado
       
        if (user.isActivo() != true) {
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2410);
        }
        //verificar el password
        if (!hasher.compareHash(password, user.getContrasenia())) {
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2400);
        }

        long expiracion = System.currentTimeMillis() + Constantes.milisecondsDurationLogin;

        //Construir el token de autenticacion
        PrincipalPawplan principal = new PrincipalPawplan(
                user.getId(),
                user.getRole(),
                new Date(System.currentTimeMillis()),
                expiracion);

        String rawToken = null;
        try {
            //Convertir el Principal en un json
            rawToken = mapper.writeValueAsString(principal);
        } catch (Exception e) {
            logger.error("No se pudo procesar la creacion del token.", e);
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2000);
        }

        //generar token a devolver
        String encodedToken = encriptador.encriptar(rawToken);

        rs.setToken(encodedToken);
        rs.setRol(user.getRole().getRole());
        
        switch (user.getRole()) {
            case PACIENTE:
                    Cliente cliente = (Cliente) user;
                    rs.setNombre(cliente.getNombre() + " " + cliente.getApellido());
                    break;
            case VETERINARIA:
                    Veterinaria veterinaria = (Veterinaria) user;
                    rs.setNombre(veterinaria.getRazonSocial());
                    break;
            case VETERINARIO:
                    Veterinario veterinario = (Veterinario) user;
                    rs.setNombre(veterinario.getNombre() + " " + veterinario.getApellido());
                    break;
            default:
                    throw new IllegalArgumentException("Unexpected value: " + user.getRole());
        }
        
        
        return rs;

    }
    
	/**
	 * Obtener el {@link AuthenticationBit}, conteniendo el {@link PrincipalBit} relacionado al token de autenticacion
	 * 
	 * @param encodedToken
	 * 
	 * @return Si esta todo bien se devuelve el objeto AuthenticationBit,pero si hay algun problema se retorna null. 
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public AuthenticationPawPlan getAuthtentication(String encodedToken) {
		
		PrincipalPawplan principal = null;
		
		try {		
			
			String rawToken = encriptador.desencriptar(encodedToken);
			principal = mapper.readValue(rawToken, PrincipalPawplan.class);
			
		} catch (GeneralSecurityException se) {
			logger.warn("Token invalido, no puede ser desencriptado : " + encodedToken, se);
			return null;
			
		} catch (Exception e) {
			logger.warn("No se puede trabajar con el token : " + encodedToken, e);
			return null;
		}
		
		AuthenticationPawPlan result = new AuthenticationPawPlan(principal);
		
		return result;
	}
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String hashPassword(String rawPassword) {
		return hasher.hash(rawPassword);
	}
}
