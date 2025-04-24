import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { DomicilioGetRs } from '../model/DomicilioGetRs';
import { Domicilio } from '../model/Domicilio';
import { Response } from '../model/Response';
import { DomicilioRq } from '../model/DomicilioRq';

@Injectable({
  providedIn: 'root'
})
export class DomicilioService {

  baseURL = CONFIG.urlBackend;

  //Datos locales
  // domiciliosData = new Observable<DomicilioGetRs>((observer) => {
  //   observer.next({
  //     estado: 'ok',
  //     mensaje: 'oke',
  //     domicilios: [
  //       {
  //         ciudad: {
  //           id: 0,
  //           provincia: {
  //             pais: {nombre: 'Argentina'},
  //             nombre: 'Córdoba'
  //           },
  //           nombre: 'Villa Maria'
  //         },
  //         calle: 'remedios de escalada',
  //         numero: '123',
  //         observaciones: 'casa bordo'
  //       },
  //       {
  //         ciudad: {
  //           id: 1,
  //           provincia: {
  //             pais: {nombre: 'Argentina'},
  //             nombre: 'Córdoba'
  //           },
  //           nombre: 'Macros Juarez'
  //         },
  //         calle: 'san juan',
  //         numero: '134',
  //         observaciones: 'casa de dos pisos'
  //       }
  //     ]
  //   });
  //   observer.complete(); // Finaliza el flujo de datos
  // });
  
  constructor(
    private http: HttpClient
  ) { }

  getDoms(cuil: string):Observable<Domicilio[]>{
    //return this.domiciliosData;
    return this.http.get<Domicilio[]>(this.baseURL + "/domicilio/findByClient/" + cuil);
  }

  eliminar(rq:DomicilioRq): Observable<Response>{
    return this.http.post<Response>(this.baseURL+"/domicilio/delete",rq)
  }

  agregar(rq: DomicilioRq): Observable<Domicilio>{
    return this.http.post<Domicilio>(this.baseURL+"/domicilio/crear",rq);
  }

}
