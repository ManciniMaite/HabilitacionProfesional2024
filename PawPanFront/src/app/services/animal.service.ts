import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CONFIG } from '../config';
import { Observable } from 'rxjs';
import { Animal } from '../model/Animal';
import { DataAnimal } from '../model/data/data-Animales';
import { AnimalGetRs } from '../model/AnimalGetRs';
import { AnimalRq } from '../model/AnimalRq';
import { AnimalRs } from '../model/AnimalRs';

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  baseURL = CONFIG.urlBackend;
  animalesData = new Observable<AnimalGetRs>((observer) => {
    observer.next({
      estado: 'ok',
      mensaje: 'oke',
      animales: [
        DataAnimal,
        {
          id:0,
          esActivo: true,
          nombre: 'Tobi',
          fechaNac: '05/05/12',
          peso: 4.5,
          foto: 'Foto del animal',
          raza: {
              id:1,
              nombre: 'Persa',
              especie: {
                  id:1,
                  nombre: 'Gato',
                  tipoEspecie: {
                    id:1,
                    nombre: 'Chico',
                    descripcion: 'Animales pequeños'
                  }
              },
          }
      }
      ]
    });
    observer.complete(); // Finaliza el flujo de datos
  });
  
  constructor(
    private http: HttpClient
  ) { }

  getAnimales(cuil: string):Observable<AnimalGetRs>{
    return this.http.get<AnimalGetRs>(this.baseURL + "/animal/findByClient/"+cuil);
  }

  crearAnimal(rq: AnimalRq):Observable<AnimalRs>{
    return this.http.post<AnimalRs>(this.baseURL + "/animal/crear", rq);
  }

  updateAnimal(rq: AnimalRq):Observable<AnimalRs>{
    return this.http.put<AnimalRs>(this.baseURL + "/animal/update", rq);
  }

  deleteAnimales(id: number):Observable<AnimalGetRs>{
    return this.http.delete<AnimalGetRs>(this.baseURL + "/animal/delete/"+id);
  }

}
