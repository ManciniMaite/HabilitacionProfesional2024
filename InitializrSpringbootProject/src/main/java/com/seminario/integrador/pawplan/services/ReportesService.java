package com.seminario.integrador.pawplan.services;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.integrador.pawplan.controller.values.ReporteTurnoPorEspecie;
import com.seminario.integrador.pawplan.controller.values.turnosPorVeterinario;
import com.seminario.integrador.pawplan.controller.values.PorcentajeTurnosAtendAceptReserPorVeterinario;
import com.seminario.integrador.pawplan.controller.values.ReportePorcentajeEstado;
import com.seminario.integrador.pawplan.controller.values.ReporteRq;
import com.seminario.integrador.pawplan.repository.ReportesRepository;
import com.seminario.integrador.pawplan.security.PrincipalPawplan;
import com.seminario.integrador.pawplan.security.Role;
import com.seminario.integrador.pawplan.security.utils.IAuthenticationFacade;

@Service
public class ReportesService {
    @Autowired
	private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ReportesRepository reportesRepository;

    public List<ReporteTurnoPorEspecie> reporteTurnoPorEspecie(ReporteRq request){

        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();

        return this.reportesRepository.turnosAtendidosPorEspecie(principalPawplan.getRole().name(),principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin());
    }

    public List<ReportePorcentajeEstado> porcentajeEstados(ReporteRq request){
        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();

        return this.reportesRepository.porcentajesTurnosPorEstado(principalPawplan.getRole().name(),principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin());
    }

    public float tasaCancelacionRechazo(ReporteRq request){
        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
        //System.out.println("ROL: " + principalPawplan.getRole().name() + " ID: " + principalPawplan.getClienteId());
        return this.reportesRepository.tasaCancelacionRechazo(principalPawplan.getRole().name(),principalPawplan.getClienteId(),request.getFechaInicio(), request.getFechaFin());
    }

    public float tasaAtencion(ReporteRq request){
        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
        //System.out.println("ROL: " + principalPawplan.getRole().name() + " ID: " + principalPawplan.getClienteId());
        return this.reportesRepository.tasaDeAtencion(principalPawplan.getRole().name(),principalPawplan.getClienteId(),request.getFechaInicio(), request.getFechaFin());
    }

    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> atendidosPorVeterinario(ReporteRq request){
    //     PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
    //     return this.reportesRepository.atendidosPorVeterinario(principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin());
    // }

    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> turnosFavorablePorVeterinario(ReporteRq request){
    //     PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
    //     return this.reportesRepository.porcentajePorVeterinarios(principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin());
    // }

    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> turnosRechazadosPorVeterinario(ReporteRq request){
    //     PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
    //     return this.reportesRepository.porcentajeRechazadosPorVeterinarios(principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin());
    // }

    public List<PorcentajeTurnosAtendAceptReserPorVeterinario> obtenerPorcentajePorVeterinario(ReporteRq request){
        PrincipalPawplan principalPawplan = authenticationFacade.getPrincipal();
       String idEstados = request.getIdsEstados().stream()
                                      .map(String::valueOf) 
                                      .collect(Collectors.joining(","));
        return this.reportesRepository.obtenerPorcentajePorVeterinario(principalPawplan.getClienteId() ,request.getFechaInicio(),request.getFechaFin(),idEstados);
    }

}
