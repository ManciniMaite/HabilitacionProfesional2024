import { Response } from "./Response";

export class SessionManagerResponse extends Response{
    token: string;
    rol: string;
    nombre: string;
    cuil: string;
}