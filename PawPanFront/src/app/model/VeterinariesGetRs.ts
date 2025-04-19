import { Response } from "./Response";
import { Veterinaria } from "./Veterinaria";
import { VeterinariaXciudad } from "./veterinariaXciudad";
import { Veterinario } from "./Veterinario";
import { VeterinarioXciudad } from "./veterinarioXciudad";

export class VeterinariesGetRs extends Response{
    veterinariosIndependientes: VeterinarioXciudad[];
    veterinarias: VeterinariaXciudad[];
}