import { ClienteDTO } from "./ClienteDTO";
import { Response } from "./Response";

export class ClientesDeVeterinaria extends Response {
    clientes: ClienteDTO[];
}