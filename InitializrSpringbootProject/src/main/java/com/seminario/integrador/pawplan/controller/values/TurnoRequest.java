package com.seminario.integrador.pawplan.controller.values;

import java.util.Date;
import java.util.List;

import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Ciudad;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.TipoEspecie;

import lombok.Data;

@Data
public class TurnoRequest {

	private Date fechaConsulta;

	private TipoEspecie tipoEspecie;
	private Ciudad ciudad;
	private Date fechaReserva; 
	private Estado estado;
    private Long veterinariaId;
    private Long veterinarioId;
    private boolean esADomicilio;
    private String descripcionPrivada;
    private String descripcionPublica;
    private boolean esGuardia;
    private List<Animal> animales;
	
    private Long turnoId;
	
	
}
