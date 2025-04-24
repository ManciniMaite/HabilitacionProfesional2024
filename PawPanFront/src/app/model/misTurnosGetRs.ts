import { Response } from "./Response";

export class misTurnosGetRs extends Response {
    turnos : {
        id : number;
        raza : string;
        especie : string;
        fecha : Date;
        idAnimal : number;
        idVeterinario :number;
        idVeterinaria : number;
        nombreAnimal : string;
        nombreVeterinario : string;
        nombreVeterinaria : string;
    }[];
    total : number;
}