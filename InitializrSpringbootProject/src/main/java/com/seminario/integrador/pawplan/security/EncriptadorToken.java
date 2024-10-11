/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.security;


import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;

import com.seminario.integrador.pawplan.Constantes;
/**
 *
 * @author sebastian
 */
@Component
public final class EncriptadorToken {
	
	private Logger logger = LoggerFactory.getLogger(EncriptadorToken.class);

	private BytesEncryptor encrytor;
	
	public EncriptadorToken() {
		//inicializarEncryptor();
	}
	    
    
	/**
	 * Devuelve un String con encoding base64 que contiene el mensajes encriptado
	 * 
	 * @param mensaje
	 * 
	 * @return
	 */
	public synchronized String encriptar(String mensaje) {
		
		if(encrytor== null) {
			inicializarEncryptor();
		}
		
		byte[] mensajeEncriptado = encrytor.encrypt( mensaje.getBytes() );
		
		String result = Base64.getEncoder().encodeToString( mensajeEncriptado );
		
		return result;
	}
	
	
	/**
	 * 
	 * @param mensaje Mensaje en base64 que posee un mensaje encriptado
	 * 
	 * @return
	 */
	public synchronized String desencriptar(String mensaje) throws GeneralSecurityException {
		
		if(encrytor== null) {
			inicializarEncryptor();
		}
		byte[] mensajeEncriptado = Base64.getDecoder().decode( mensaje.getBytes() );
		
		String result = new String( encrytor.decrypt( mensajeEncriptado ) );
				
		return result;
		
	}
	
	
    /**
     * 
     * @param keyBytes
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public synchronized void inicializarEncryptor() {

    	String password = Constantes.bitsaGetCryptoPassword;
    	String salt = Constantes.bitsaGetCryptoSalt;
        
    	this.encrytor = Encryptors.standard(password, salt);
    	
    }
    
}
