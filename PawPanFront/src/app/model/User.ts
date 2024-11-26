import { Role } from "./Role";

export class User {
    id: number;
    telefono: string;
    role: Role;
    correo: string;
    contrasenia: string;
    isActivo: boolean = true;
}