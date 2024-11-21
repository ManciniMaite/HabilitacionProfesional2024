import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EspecieAdmRs } from '../model/EspecieAdmRs';

@Injectable({
  providedIn: 'root'
})
export class EspecieService {
  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  getAll():Observable<EspecieAdmRs>{ 
    return this.http.get<EspecieAdmRs>(this.baseURL + "/especies");
  }
}
