import { ProfesionalesPorVeterinaria } from "./ProfesionalPorVeterinaria";
import { Response } from "./Response";
import { Veterinario } from "./Veterinario";

export class ProfesionalesPorVeterinariaRs extends Response {
    profesionales: ProfesionalesPorVeterinaria[]
}