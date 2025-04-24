import { Role } from "./Role";

export class User {
    id: number;
    telefono: string;
    role: string;
    correo: string;
    contrasenia: string;
    activo: boolean = true;

}