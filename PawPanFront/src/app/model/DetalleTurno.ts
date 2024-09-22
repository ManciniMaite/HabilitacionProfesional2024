import { Animal } from "./Animal";
import { TipoConsulta } from "./TipoConsulta";

export class DetalleTurno {
    monto: number;
    tipoConsulta: TipoConsulta;
    cantidad: number;
    descripcion: string;
    animal: Animal;
}