import { AnimalDTO } from "./AnimalDTO";

export class ClienteDTO {
    clienteId: number;
    clienteNombre: string;
    clienteApellido: string;
    dni: string ;
    animales: AnimalDTO[];
  }