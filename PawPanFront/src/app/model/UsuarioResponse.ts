import { Cliente } from "./Cliente";
import { Response } from "./Response";

export class UsuarioResponse extends Response{
    usuario: Cliente	
	pregunta: string;
}