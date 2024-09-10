import { Especie } from "./Especie";
import { Raza } from "./Raza";

export class Animal {
    esActivo: boolean;
    nombre: string;
    especie: Especie;
    fechaNac: string; //DAte??
    peso: number;
    foto: string;
    raza: Raza;
}