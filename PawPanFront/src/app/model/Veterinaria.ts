import { Horario } from "./Horario";
import { TipoEspecie } from "./TipoEspecie";
import { User } from "./User";
import { Veterinario } from "./Veterinario";

export class Veterinaria extends User{
    razonSocial: string;
    cuit: string;
    haceGuardia: boolean;
    aptoCirugia: boolean;
    horarioAtencion?: {
        id: number;
        dia: string;
        horario: Horario;
    }[]
    veterinarios: Veterinario[];
    haceDomicilio: boolean;
    
}