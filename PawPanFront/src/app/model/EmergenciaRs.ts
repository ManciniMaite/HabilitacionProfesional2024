import { Emergencia } from "./Emergencia";
import { Response } from "./Response";

export class EmergenciaRs extends Response {
    contactos: Emergencia[];
}