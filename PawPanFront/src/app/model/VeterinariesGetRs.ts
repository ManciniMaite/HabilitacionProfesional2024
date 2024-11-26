import { Response } from "./Response";
import { Veterinaria } from "./Veterinaria";
import { Veterinario } from "./Veterinario";

export class VeterinariesGetRs extends Response{
    veterinariosIndependientes: Veterinario[];
    veterinarias: Veterinaria[];
}