package com.seminario.integrador.pawplan.services;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.seminario.integrador.pawplan.controller.values.Matriculado;
import com.seminario.integrador.pawplan.controller.values.MatriculadosResponse;
import com.seminario.integrador.pawplan.controller.values.Response;
import com.seminario.integrador.pawplan.controller.values.ValidarMatriculaRq;

@Service
public class MatriculaService {
    private final RestTemplate restTemplate;

    public MatriculaService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Response validarMatricula(ValidarMatriculaRq rq) {
        Response rs = new Response();
        String url = "https://cmvc.soportedlr.com.ar/api/v1/matriculados-pagina";
        
        ResponseEntity<MatriculadosResponse> response = restTemplate.getForEntity(url, MatriculadosResponse.class);

        if (response.getStatusCode().is2xxSuccessful() ) {
            if(response.getBody() != null){
                if(!response.getBody().getRegistros().isEmpty() && response.getBody().getRegistros() != null){
                    List<Matriculado> registros = response.getBody().getRegistros();

                    Matriculado matriculado = registros.stream().filter(m -> rq.getNroMatricula().equals(m.getXmatricula())).findFirst().orElse(null);
                    
                    if(matriculado != null){
                        if(matriculado.getDni().equals(rq.getDni())){
                            if(matriculado.getCategoria().equals("ACTIVO A")){
                                rs.setEstado("OK");
                                rs.setMensaje("Matricula verificada!");
                                return rs;
                            } else{
                                rs.setEstado("ERROR");
                                rs.setMensaje("Matricula NO habilitada");
                                return rs;
                            }
                        }else{
                            rs.setEstado("ERROR");
                            rs.setMensaje("La matricula no corresponde al DNI ingresado");
                            return rs;
                        }
                    } else{
                        rs.setEstado("ERROR");
                        rs.setMensaje("No existe un matriculado con la matricula indicada");
                        return rs;
                    }
                }else {
                    rs.setEstado("ERROR");
                    rs.setMensaje("Sin datos de registros");
                    return rs;
                }
            } else{
                rs.setEstado("ERROR");
                rs.setMensaje("No hay cuerpo de respuesta");
                return rs;
            }
        } else{
            rs.setEstado("ERROR");
            rs.setMensaje("Falló el llamado a la API de matriculas");
            return rs;
        }
    }
}
