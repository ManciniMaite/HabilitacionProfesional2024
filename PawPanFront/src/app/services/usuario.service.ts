import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { UsuarioRequest } from '../model/UsuarioRq';
import { UsuarioResponse } from '../model/UsuarioResponse';

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

  getClinteByDni(dni:string):Observable<UsuarioResponse>{
    return this.http.get<UsuarioResponse>(this.baseURL+"/Usuario/"+dni)
  }

  obtenerPregunta(rq:UsuarioRequest):Observable<UsuarioResponse>{
    return this.http.post<UsuarioResponse>(this.baseURL+"/Usuario/RecuperarContrasena", rq)
  }

  respuestaSecreta(rq:UsuarioRequest):Observable<UsuarioResponse>{
    return this.http.post<UsuarioResponse>(this.baseURL+"/Usuario/preguntaSecreta", rq)
  }

  nuevaPassword(rq:UsuarioRequest):Observable<UsuarioResponse>{
    return this.http.post<UsuarioResponse>(this.baseURL+"/Usuario/NuevaContrasena", rq)
  }

}
