import { Horario } from "./Horario";
import { HorarioDisponibilidad } from "./HorarioDisponibilidad";
import { Response } from "./Response";

export class DisponibilidadTurnoRs extends Response {
    horariosDisponibles:HorarioDisponibilidad[];
    turno: any;
    estadoReserva:any
}