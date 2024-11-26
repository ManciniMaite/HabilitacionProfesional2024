import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { VeterinariesGetRs } from '../model/VeterinariesGetRs';
import { veterinarioMock } from '../model/data/data-Veterinario';
import { veterinariaMock } from '../model/data/data-Veterinaria';

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

  getAll(idCiudad: number):Observable<VeterinariesGetRs>{
    return this.VeterinariesData;
    // return this.http.post<VeterinariesGetRs>(this.baseURL + "/veterinaries/" + idCiudad);
  }

}
