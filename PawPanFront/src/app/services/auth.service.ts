import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { SessionManagerRequest } from '../model/SessionManagerRequest';
import { SessionManagerResponse } from '../model/SessionManagerResponse';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseURL = CONFIG.urlBackend;
  
  constructor(
    private http: HttpClient
  ) { }

  onLogIn(rq: SessionManagerRequest): Observable<SessionManagerResponse>{
    return this.http.post<SessionManagerResponse>(this.baseURL + "/SessionManager", rq);
  }

}
