/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author sebastian
 */
@Component
public class Hasher {

	private BCryptPasswordEncoder hasher = new BCryptPasswordEncoder();
	
	public synchronized String hash(String rawMensaje) {
		return hasher.encode(rawMensaje);
	}
	
	
	public synchronized boolean compareHash(String rawMensaje, String encodedMensaje) {
		return hasher.matches(rawMensaje, encodedMensaje);
	}
}