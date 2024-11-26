import { Horario } from "./Horario";
import { User } from "./User";

export class Veterinario extends User{
    nombre: string;
    apellido: string;
    dni: string;
    fechaNac: string;
    matricula: number;
    esIndependiente: boolean;
    haceGuardia: boolean;
    horario: {
        id: number;
        dia: string;
        horario: Horario;
    }[];
    especialidad: {
        id: number;
        nombre: string;
    }[];
    haceDomicilio: boolean;
}