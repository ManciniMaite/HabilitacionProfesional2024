import { Horario } from "./Horario";
import { Response } from "./Response";

export class DisponibilidadRs extends Response {
    horarios: Horario[];
}