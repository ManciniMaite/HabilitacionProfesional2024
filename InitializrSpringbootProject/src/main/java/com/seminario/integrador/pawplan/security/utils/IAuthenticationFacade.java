package com.seminario.integrador.pawplan.security.utils;

import org.springframework.security.core.Authentication;

import com.seminario.integrador.pawplan.security.PrincipalPawplan;

public interface IAuthenticationFacade {
	public Authentication getAuthentication();

	public PrincipalPawplan getPrincipal();
}
