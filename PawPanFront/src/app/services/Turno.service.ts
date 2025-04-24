import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable, of } from 'rxjs';
import { VeterinariesGetRs } from '../model/VeterinariesGetRs';
import { DisponibilidadRq } from '../model/DisponibilidadRq';
import { DisponibilidadRs } from '../model/DisponibilidadRs';
import { DisponibilidadTurnoRs } from '../model/DisponibilidadTurnoRs';
import { DATA_DISPONIBILIDAD_TURNO } from '../model/data/data-disponibilidadTurnoRs';
import { ReservarTurnoRq } from '../model/TurnoRq';
import { Response } from '../model/Response';
import { Turno } from '../model/Turno';
import { TURNOBYID } from '../model/data/data-TurnoById';
import { TurnoRs } from '../model/TurnoRs';
import { AtenderTurnoRq } from '../model/AtenderTurnoRq';
import { FiltroTurnoRq } from '../model/FiltroTurnoRq';

@Injectable({
  providedIn: 'root'
})
export class TurnoService {

  baseURL = CONFIG.urlBackend;

  //Datos locales
  horariosData = new Observable<DisponibilidadRs>((observer) => {
    observer.next({
        estado: 'ok',
        mensaje: 'oke',
        horarios: [
          {
            id:0,
            horaInicio: '08:00',
            horaFin: '09:00'
          },
          {
            id:1,
            horaInicio: '09:00',
            horaFin: '10:00'
          },
          {
            id:3,
            horaInicio: '10:00',
            horaFin: '11:00'
          },
        ]
    });
    observer.complete(); // Finaliza el flujo de datos
  });
  
  constructor(
    private http: HttpClient
  ) { }

  // insert(idCiudad: number):Observable<VeterinariesGetRs>{ //Cambiar Rq y Rs
  //   return this.VeterinariesData;
  //   return this.http.post<VeterinariesGetRs>(this.baseURL + "/Turno/Reservar" + idCiudad);
  // }
  
  disponibilidad(rq: DisponibilidadRq):Observable<DisponibilidadTurnoRs>{
    //return of(DATA_DISPONIBILIDAD_TURNO);
    console.log("Fecha: " + rq.fecha);
    return this.http.post<DisponibilidadTurnoRs>(this.baseURL + "/Turno/Consultar", rq);
  }
  
  getById(id:number): Observable<Turno>{
    return this.http.get<Turno>(this.baseURL+"/Turno/"+id);
  }

  getMisTurnos(rq: FiltroTurnoRq):Observable<any>{
    return this.http.post<any>(this.baseURL+"/Turno/getMisTurnos",rq)
  }


  reservar(rq: ReservarTurnoRq): Observable<Response>{
    return this.http.post<Response>(this.baseURL+"/Turno/Reservar", rq);
  }

  atender(rq: AtenderTurnoRq):Observable<TurnoRs>{
    return this.http.post<TurnoRs>(this.baseURL+"/Turno/Atender", rq);
  }

  cancelar(rq: AtenderTurnoRq):Observable<TurnoRs>{
    return this.http.post<TurnoRs>(this.baseURL+"/Turno/Cancelar", rq);
  }

  rechazar(rq: AtenderTurnoRq):Observable<TurnoRs>{
    return this.http.post<TurnoRs>(this.baseURL+"/Turno/Rechazar", rq);
  }

  aceptar(rq: AtenderTurnoRq):Observable<TurnoRs>{
    return this.http.post<TurnoRs>(this.baseURL+"/Turno/Aceptar", rq);
  }
}
