import { Animal } from "./Animal";
import { Estado } from "./Estado";
import { Veterinaria } from "./Veterinaria";
import { Veterinario } from "./Veterinario";


export class Turno {
    id:number;
    fechaHoraReserva: string;
    fechaHora: string;
    animal: Animal;
    estado: Estado;   
    veterinaria?:Veterinaria;
    veterinario:Veterinario ;
    esADomicilio: boolean;
    descripcionPublica: string | null;
    descripcionPrivada: string | null;
}