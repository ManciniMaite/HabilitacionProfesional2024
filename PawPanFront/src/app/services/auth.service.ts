import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionManagerRequest } from '../model/SessionManagerRequest';
import { SessionManagerResponse } from '../model/SessionManagerResponse';
import { HttpClient } from '@angular/common/http';
import { L } from '@angular/cdk/keycodes';
import { Usuario } from '../model/Usuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseURL = CONFIG.urlBackend;

  private usuarioSubject = new BehaviorSubject<Usuario>(this.getUsuario());
  usuario$ = this.usuarioSubject.asObservable();
  
  constructor(
    private http: HttpClient
  ) { }

  onLogIn(rq: SessionManagerRequest): Observable<SessionManagerResponse>{
    return this.http.post<SessionManagerResponse>(this.baseURL + "/SessionManager", rq);
  }

  private getUsuario() {
    const usuarioGuardado = localStorage.getItem('usuario');
    return usuarioGuardado ? JSON.parse(usuarioGuardado) : null;
  }

  setUsuario(token: string, usuario: string, rol: string){
    console.log('setUs')
    // localStorage.setItem('token', token);
    // localStorage.setItem('usuario', usuario);
    // localStorage.setItem('rol', rol);
    let us: Usuario = new Usuario();
    us.nombre=usuario;
    us.token=token;
    us.rol=rol;
    this.usuarioSubject.next(us);
  }

}
