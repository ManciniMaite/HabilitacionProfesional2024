export class FiltroTurnoRq {
    idAnimal: number //filtra solo para el cliente
    idCliente: number; //no filtra se setea por session
    idVeterinaria: number; //filtra solo para la veterinaria sino se setea por session
    idVeterinario: number; //no filtra se setea por session
    nEstado: string; //data-Estados esta el nombre igual que en bd
    fecha: string; //segun datepicker

    page: number;
    size: number;
    orderDir: string;
    orderBy: string;
}
