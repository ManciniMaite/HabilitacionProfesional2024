import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { UsuarioRq } from '../model/UsuarioRq';
import { Animal } from '../model/Animal';
import { DataAnimal } from '../model/data/data-Animales';
import { AnimalGetRs } from '../model/AnimalGetRs';

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  baseURL = CONFIG.urlBackend;
  animalesData = new Observable<AnimalGetRs>((observer) => {
    observer.next({
      estado: 'ok',
      mensaje: 'oke',
      animales: [DataAnimal,DataAnimal]
    });
    observer.complete(); // Finaliza el flujo de datos
  });
  
  constructor(
    private http: HttpClient
  ) { }

  getAnimales(cuil: number):Observable<AnimalGetRs>{
    return this.animalesData;
    // return this.http.post<Animal[]>(this.baseURL + "/animal/get", cuil);
  }

}
