import { Ciudad } from "./Ciudad";

export class Domicilio {
    id:number
    ciudad: Ciudad;
    calle: string;
    numero: string;
    observaciones: string //dpto, etc
}