import { Horario } from "./Horario";
import { TipoEspecie } from "./TipoEspecie";
import { User } from "./User";
import { Veterinaria } from "./Veterinaria";

export class Veterinario extends User{
    nombre: string;
    apellido: string;
    dni: string;
    fechaNac: string;
    matricula: string;
    esIndependiente: boolean;
    haceGuardia: boolean;
    horarios?: {
        id: number;
        dia: string;
        horario: Horario;
    }[] ;
    haceDomicilio: boolean;
    veterinaria?:Veterinaria
    tiposEspecie: TipoEspecie[];
}