import { Response } from "./Response";
import { TurnoFb } from "./TurnoFb";

export class PaginaTurnosRs extends Response{
    turnos:TurnoFb;
    total: number;
}