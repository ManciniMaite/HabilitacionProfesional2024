import { AnimalRq } from "./AnimalRq";

export class ReservarTurnoRq {
    veterinariaId: number;
    veterinarioId: number;
    animalId: number;
    fecha: string;
    hora: string;
    esDomicilio: boolean;

    dniCliente:string;  //En caso de necesitar crear un nuevo cliente
    animalRq: AnimalRq; //En caso de necesitar crear un nuevo animal
}