import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { DomicilioGetRs } from '../model/DomicilioGetRs';
import { Domicilio } from '../model/Domicilio';

@Injectable({
  providedIn: 'root'
})
export class DomicilioService {

  baseURL = CONFIG.urlBackend;

  //Datos locales
  domiciliosData = new Observable<DomicilioGetRs>((observer) => {
    observer.next({
      estado: 'ok',
      mensaje: 'oke',
      domicilios: [
        {
          ciudad: {
            id: 0,
            provincia: {
              pais: {nombre: 'Argentina'},
              nombre: 'Córdoba'
            },
            nombre: 'Villa Maria'
          },
          calle: 'remedios de escalada',
          numero: '123',
          observaciones: 'casa bordo'
        },
        {
          ciudad: {
            id: 1,
            provincia: {
              pais: {nombre: 'Argentina'},
              nombre: 'Córdoba'
            },
            nombre: 'Macros Juarez'
          },
          calle: 'san juan',
          numero: '134',
          observaciones: 'casa de dos pisos'
        }
      ]
    });
    observer.complete(); // Finaliza el flujo de datos
  });
  
  constructor(
    private http: HttpClient
  ) { }

  getDoms(cuil: string):Observable<Domicilio[]>{
    //return this.domiciliosData;
    return this.http.get<Domicilio[]>(this.baseURL + "/domicilio/findByClient/" + cuil);
  }

}
