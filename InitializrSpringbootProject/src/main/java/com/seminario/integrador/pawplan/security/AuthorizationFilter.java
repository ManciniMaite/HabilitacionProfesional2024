package com.seminario.integrador.pawplan.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.seminario.integrador.pawplan.Constantes;

@Component
@Order(1)
public class AuthorizationFilter implements Filter	 {

	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Si es un intento de login, no hacemos nada y dejamos que continue.
		if (   true || Constantes.URL_PATH_SESSION_MANAGER.equals( ((HttpServletRequest) request).getServletPath() )
			|| (Constantes.URL_PATH_USUARIO+Constantes.URL_PATH_CREAR).equals( ((HttpServletRequest) request).getServletPath() )
			|| (Constantes.URL_PATH_USUARIO+Constantes.URL_PATH_CONSULTAR).equals( ((HttpServletRequest) request).getServletPath() )
			|| /*Constantes.URL_PATH_HEARTBEAT.equals( ((HttpServletRequest) request).getServletPath() )
			|| Constantes.URL_PATH_ACERCA_DE.equals( ((HttpServletRequest) request).getServletPath() )
			||*/ Constantes.URL_PATH_HASH.equals( ((HttpServletRequest) request).getServletPath() )
			/*|| (((HttpServletRequest) request).getServletPath()).startsWith(Constantes.URL_PATH_ADMIN_ACTUATOR)
			|| (((HttpServletRequest) request).getServletPath()).startsWith(Constantes.URL_PATH_CONTAINER)
			|| (((HttpServletRequest) request).getServletPath()).startsWith(Constantes.URL_PATH_ANONIMO)*/
		)  {
            chain.doFilter(request, response);
            return;
		}
		
		//Si es una peticion comun, entonces validamos que tenga un token valido.
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        Enumeration<String> headerValues = httpRequest.getHeaders("Authorization");

        if (headerValues != null) {
	        while (headerValues.hasMoreElements()) {
	        	String autorizacion = headerValues.nextElement();
	        	
	            if ( autorizacion.startsWith("Pawplan") ) { //La cabecera de validacion de Bit se encuentra presente?.
	            	
	            	//setear los datos del usuario en el contexto de seguridad
	            	Authentication authentication = sessionManager.getAuthtentication( autorizacion.substring(autorizacion.indexOf(" ")) ); //Enviar el token para ser procesado
	            	
	            	if (authentication == null) {
	            		break;
	            	}
	            	
	            	if (((PrincipalPawplan)authentication.getPrincipal()).getLoginDateExpiration() < System.currentTimeMillis()) {
	            		break;
	            	}
	            	
	            	//Si es una pagina de administracion del sitio, debera ser un usuario administrador de plataforma
	            	if ( (((HttpServletRequest) request).getServletPath()).matches(Constantes.URL_PATH_ADMIN_PLATFORM)){
	            		if ( ((PrincipalPawplan)authentication.getPrincipal()).getRole() != Role.PLATFORM_ADMIN ) {
	            			break;
	            		}	            		
	            	}
	            	
	            	SecurityContextHolder.getContext().setAuthentication(authentication);
	            	
	                chain.doFilter(request, response);
	                return;
	            }                	
	        }
        }
        

        /* Si llegamos a este punto es que no se paso la validacion.
         * Devolver 401 = No Autorizado 
         */
        System.out.println("Acceso no autorizado");        
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token de autenticacion invalido.");
		
	}
}
