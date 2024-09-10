import { Cliente } from "./Cliente";
import { DetalleTurno } from "./DetalleTurno";
import { Estado } from "./Estado";
import { Veterinaria } from "./Veterinaria";
import { Veterinario } from "./Veterinario";

export class Turno {
    fechaHoraReserva: string;
    feha: string;
    hora: string;
    detalleTurno: DetalleTurno[];
    estado: Estado;
    cliente: Cliente;
    veterinaria: Veterinaria;
    veterinario: Veterinario;
    esADomicilio: boolean;
    duracionEstimada: string;
    descripcion: string;
    monto: number;
    esGuardia: boolean;
}