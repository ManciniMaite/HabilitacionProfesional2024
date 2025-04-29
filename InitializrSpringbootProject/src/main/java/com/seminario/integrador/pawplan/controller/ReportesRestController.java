package com.seminario.integrador.pawplan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seminario.integrador.pawplan.controller.values.ReporteTurnoPorEspecie;
import com.seminario.integrador.pawplan.controller.values.turnosPorVeterinario;
import com.seminario.integrador.pawplan.controller.values.PorcentajeTurnosAtendAceptReserPorVeterinario;
import com.seminario.integrador.pawplan.controller.values.ReportePorcentajeEstado;
import com.seminario.integrador.pawplan.controller.values.ReporteRq;
import com.seminario.integrador.pawplan.services.ReportesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/reportes/")
public class ReportesRestController {
    @Autowired
    private ReportesService reportesService;

    @PostMapping("turnosPorEspecie")
    public List<ReporteTurnoPorEspecie> turnosPorEspecie(@RequestBody ReporteRq rq) {
        return this.reportesService.reporteTurnoPorEspecie(rq);
    }
    
    @PostMapping("porcentajeEstados")
    public List<ReportePorcentajeEstado> posrcentajeEstado(@RequestBody ReporteRq rq) {
        return this.reportesService.porcentajeEstados(rq);
    }

    @PostMapping("tasaCancelacionRechazo")
    public float tasaCancelacionRechazo(@RequestBody ReporteRq rq) {
        return this.reportesService.tasaCancelacionRechazo(rq);
    }

    @PostMapping("tasaAtencion")
    public float tasaDeAtencion(@RequestBody ReporteRq rq) {
        return this.reportesService.tasaAtencion(rq);
    }

    // @PostMapping("atendidosPorVeterinario")
    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> atendidosPorVeterinario(@RequestBody ReporteRq rq) {
    //     return this.reportesService.atendidosPorVeterinario(rq);
    // }

    // @PostMapping("porcentajeFavorablePorVeterinario")
    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> favorableVeterinarios(@RequestBody ReporteRq rq) {
    //     return this.reportesService.turnosFavorablePorVeterinario(rq);
    // }

    // @PostMapping("porcentajeRechazadoPorVeterinario")
    // public List<PorcentajeTurnosAtendAceptReserPorVeterinario> rechazadoPorVeterinario(@RequestBody ReporteRq rq) {
    //     return this.reportesService.turnosRechazadosPorVeterinario(rq);
    // }
    
    @PostMapping("obtenerPorcentajePorVeterinario")
    public List<PorcentajeTurnosAtendAceptReserPorVeterinario> obtenerPorcentajePorVeterinario(@RequestBody ReporteRq rq) {
        return this.reportesService.obtenerPorcentajePorVeterinario(rq);
    }
}
