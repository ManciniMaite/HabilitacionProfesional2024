import { Cliente } from "./Cliente";
import { Especie } from "./Especie";
import { Raza } from "./Raza";

export class Animal {
    id:number;
    esActivo: boolean;
    nombre: string;
    fechaNac: string; //DAte??
    peso: number;
    raza: Raza;
    cliente:Cliente;
}