import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { TipoEspecie } from '../model/TipoEspecie';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TipoEspecieService {
  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  getAll():Observable<TipoEspecie[]>{ 
    return this.http.get<TipoEspecie[]>(this.baseURL + "/tipoEspecie");
  }
}
