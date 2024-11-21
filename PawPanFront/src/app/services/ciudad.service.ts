import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { HttpClient } from '@angular/common/http';
import { CiudadesAdmRs } from '../model/CidadesAdmRs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CiudadService {

  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  getAll():Observable<CiudadesAdmRs>{ 
    return this.http.get<CiudadesAdmRs>(this.baseURL + "/ciudades");
  }
}
