import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { UsuarioRequest } from '../model/UsuarioRq';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  crearCuenta(rq: UsuarioRequest):Observable<any>{ //ANY pq segun lo que se este crando es la rta puede ser con un cliente, un veterinario o una veterinaria
    return this.http.post<any>(this.baseURL + "/Usuario/Crear", rq);
  }

}
