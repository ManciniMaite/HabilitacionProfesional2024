/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seminario.integrador.pawplan.Constantes;
import com.seminario.integrador.pawplan.enums.EnumCodigoErrorLogin;
import com.seminario.integrador.pawplan.exception.PawPlanRuleException;
import com.seminario.integrador.pawplan.model.Usuario;
import com.seminario.integrador.pawplan.repository.UsuarioRepository;
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
    public String login(String correo, String password) throws PawPlanRuleException {

        //obtener los datos del usuario desde la base de datos. 
        Usuario user = usuarioRepository.findByCorreo(correo);

        //verificar si el usuario existe
        if (user == null) {
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2400);
        }

        //verificar si el usuario esta habilitado
        //TODO usar la enumeracion correspondiente y no el codigo hardcoded
        /*if (user.getStatus() != 1) {
            throw new PawPlanRuleException(EnumCodigoErrorLogin.LOGIN_2410);
        }*/
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

        return encodedToken;

    }
}
