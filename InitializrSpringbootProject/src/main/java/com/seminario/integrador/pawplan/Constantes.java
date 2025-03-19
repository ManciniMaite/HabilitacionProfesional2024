/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author sebastian
 */
public class Constantes {

    //duracion de 24hs 
    public static final long milisecondsDurationLogin = 1000 * 60 * 60 * 24;

    public static final String pawplanGetCryptoPassword = "9d3d93181c082b3791844c73786d41ca";
    public static final String pawplanGetCryptoSalt = "4cb148388500cf647daf8c4e26dffe4f";
    public static final String pawplanGetTokenduracion = "28800000";

    
    
    
    
    
    
    /***************************************************************************************/
    /***** URL **********/
    public static final String URL_PATH_ADMIN_PLATFORM = "/admin/platform";
    public static final String URL_PATH_SESSION_MANAGER = "/SessionManager";
    public static final String URL_PATH_HASH = "/Hash";
    
    public static final String URL_PATH_CREAR = "/Crear";
    public static final String URL_PATH_MODIFICAR = "/Modificar";
    public static final String URL_PATH_ELIMINAR = "/Eliminar";
    public static final String URL_PATH_CONSULTAR = "/Consultar";
    
    public static final String URL_PATH_USUARIO = "/Usuario";
    public static final String URL_PATH_TURNO = "/Turno";
    public static final String URL_PATH_RESERVAR = "/Reservar";
    
    public static final String URL_PATH_CIUDAD = "/ciudades";
    public static final String URL_PATH_RAZA = "/razas";
    public static final String URL_PATH_ESPECIE = "/especies";
    
    public static final String URL_PATH_ESPECIALIDADES = "/especialidades";
    public static final String URL_PATH_VALIDAR_MATRICULA = "/validarMatricula";
    
    
    
    
    
    
    
    /**
     * yyyy-MM-dd'T'00:00:00
     */
    public static final String FORMATO_TIMESTAMP_T_0_0X = "yyyy-MM-dd'T'00:00:00";
    /**
     * yyyy-MM-dd'T'00:00:00X
     */
    public static final String FORMATO_TIMESTAMP_T_0_1X = "yyyy-MM-dd'T'00:00:00X";
    /**
     * yyyy-MM-dd'T'00:00:00XX
     */
    public static final String FORMATO_TIMESTAMP_T_0_2X = "yyyy-MM-dd'T'00:00:00XX";
    /**
     * yyyy-MM-dd'T'00:00:00XXX
     */
    public static final String FORMATO_TIMESTAMP_T_0_3X = "yyyy-MM-dd'T'00:00:00XXX";
    /**
     * yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String FORMATO_TIMESTAMP_T_1_0X = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * yyyy-MM-dd'T'HH:mm:ssX
     */
    public static final String FORMATO_TIMESTAMP_T_1_1X = "yyyy-MM-dd'T'HH:mm:ssX";
    /**
     * yyyy-MM-dd'T'HH:mm:ssXX
     */
    public static final String FORMATO_TIMESTAMP_T_1_2X = "yyyy-MM-dd'T'HH:mm:ssXX";
    /**
     * yyyy-MM-dd'T'HH:mm:ssXXX
     */
    public static final String FORMATO_TIMESTAMP_T_1_3X = "yyyy-MM-dd'T'HH:mm:ssXXX";
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSSSSXX
     */
    public static final String FORMATO_TIMESTAMP_T_1_6S_2X = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXX";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMATO_DATE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd 00:00:00
     */
    public static final String FORMATO_TIMESTAMP_0_0X = "yyyy-MM-dd 00:00:00";
    /**
     * yyyy-MM-dd HH:mm:ssXX
     */
    public static final String FORMATO_TIMESTAMP_1_2X = "yyyy-MM-dd HH:mm:ssXX";
    /**
     * yyyy-MM-dd HH:mm:ssXX
     */
    public static final String FORMATO_TIMESTAMP_T_1_0X_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    private static ObjectMapper mapper = null;
    private static ObjectMapper mapper_ignore_properties = null;

    public static final ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setDateFormat(getSimpleDateFormatDEFAULT());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return mapper;
    }

    /**
     * El formato utilizado es el defindo por la constante
     * {@link Constantes#FORMATO_TIMESTAMP_T_1_0X}
     *
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormatDEFAULT() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_TIMESTAMP_T_0_0X);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static SimpleDateFormat getSimpleDateFormat(String formato) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }
}
