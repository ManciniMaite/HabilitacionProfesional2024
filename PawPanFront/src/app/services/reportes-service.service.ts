import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { HttpClient } from '@angular/common/http';
import { ReporteRq } from '../model/ReporteRq';
import { TurnosPorVeterinario } from '../model/TurnosPorVeterinario';
import { Observable } from 'rxjs';
import { TurnosPorEspecie } from '../model/TurnosPorEspecie';
import { PorcentajesTurnosPorEstado } from '../model/PorcentajesTurnosPorEstado';

@Injectable({
  providedIn: 'root'
})
export class ReportesServiceService {
  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  obtenerPorcentajePorVeterinario(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/obtenerPorcentajePorVeterinario" , rq)
  }

  getAtendidos(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/atendidosPorVeterinario" , rq)
  }

  getRechazados(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/porcentajeRechazadoPorVeterinario" , rq)
  }

  getPendientes(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/porcentajeFavorablePorVeterinario" , rq)
  }

  getTasaCancelacion(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/tasaCancelacionRechazo" , rq)
  }

  getTasaAtencion(rq: ReporteRq):Observable<TurnosPorVeterinario[]>{
    return this.http.post<TurnosPorVeterinario[]>(this.baseURL+"/reportes/tasaAtencion" , rq)
  }

  getTurnosPorEstados(rq: ReporteRq):Observable<PorcentajesTurnosPorEstado[]>{
    return this.http.post<PorcentajesTurnosPorEstado[]>(this.baseURL+"/reportes/porcentajeEstados" , rq)
  }

  getTurnosPorEspecie(rq: ReporteRq):Observable<TurnosPorEspecie[]>{
    return this.http.post<TurnosPorEspecie[]>(this.baseURL+"/reportes/turnosPorEspecie" , rq)
  }
}
