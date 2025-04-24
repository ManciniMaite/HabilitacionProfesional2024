import { Response } from "./Response";
import { Turno } from "./Turno";

export class TurnoRs extends Response{
    turno: Turno
    estadoReserva: string;
    turnos: Turno[];
    horariosDisponibles: any
}