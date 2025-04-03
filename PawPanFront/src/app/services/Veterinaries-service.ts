import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { VeterinariesGetRs } from '../model/VeterinariesGetRs';
import { veterinarioMock } from '../model/data/data-Veterinario';
import { veterinariaMock } from '../model/data/data-Veterinaria';
import { Horario } from '../model/Horario';
import { DiaHorarioAtencion } from '../model/DiaHorarioAtencion';
import { Response } from '../model/Response';
import { ValidarMatriculaRq } from '../model/validarMatriculaRq';

@Injectable({
  providedIn: 'root'
})
export class VeterinariesService {

  baseURL = CONFIG.urlBackend;

  //Datos locales
  VeterinariesData = new Observable<VeterinariesGetRs>((observer) => {
    observer.next({
      estado: 'ok',
      mensaje: 'oke',
      veterinariosIndependientes: veterinarioMock,
      veterinarias: veterinariaMock,
    });
    observer.complete(); // Finaliza el flujo de datos
  });
  
  constructor(
    private http: HttpClient
  ) { }

  crearDiaHorarioAtencionCorrido(dia:string, horaInicio:string, horaFin:string): DiaHorarioAtencion{
    console.log('horas: ', horaFin, horaInicio)
    let diaHorarioAtencion: DiaHorarioAtencion = new DiaHorarioAtencion();
    let horarios: Horario[] = [];
    diaHorarioAtencion.dia = dia;
    horarios.push(this.crearHorario(horaInicio,horaFin));
    diaHorarioAtencion.horarios = horarios;
    return diaHorarioAtencion;
  }

  crearDiaHorarioAtencionCortado(dia:string, horaInicio:string, horaFin:string, horaInicio2:string, horaFin2:string): DiaHorarioAtencion{
    console.log('horas: ', horaFin, horaInicio, horaFin2, horaInicio2)
    let diaHorarioAtencion: DiaHorarioAtencion = new DiaHorarioAtencion();
    let horarios: Horario[] = [];
    diaHorarioAtencion.dia = dia;
    horarios.push(this.crearHorario(horaInicio,horaFin));
    horarios.push(this.crearHorario(horaInicio2,horaFin2));
    diaHorarioAtencion.horarios = horarios;
    return diaHorarioAtencion;
  }

  crearHorario(horaInicio: string, horaFin: string): Horario{
    let horario:Horario = new Horario();
    horario.horaFin=horaFin
    horario.horaInicio=horaInicio
    console.log('horario: ', horario)
    return horario;
  }

  getAll(idCiudad: number, tipoEspecie: number):Observable<VeterinariesGetRs>{
    //return this.VeterinariesData;
    return this.http.get<VeterinariesGetRs>(this.baseURL + "/veterinaries/" + idCiudad+"/"+tipoEspecie);
  }

  validarMatricula(rq: ValidarMatriculaRq): Observable<Response>{
    return  this.http.post<Response>(this.baseURL+"/validarMatricula", rq);
  }

}
