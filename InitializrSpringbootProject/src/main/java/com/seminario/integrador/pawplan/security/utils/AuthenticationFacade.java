package com.seminario.integrador.pawplan.security.utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.seminario.integrador.pawplan.security.PrincipalPawplan;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
 
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

	@Override
	public PrincipalPawplan getPrincipal() {
		return (PrincipalPawplan) getAuthentication().getPrincipal();
	}
    
}