import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { HttpClient } from '@angular/common/http';
import { RazaAdmRs } from '../model/RazaAdmRs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RazaService {
  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  getAll(id:number):Observable<RazaAdmRs>{ 
    return this.http.get<RazaAdmRs>(this.baseURL + "/razas/"+id);
  }
}
