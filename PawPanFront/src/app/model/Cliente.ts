import { Animal } from "./Animal";
import { User } from "./User";

export class Cliente extends User {
    nombre: string;
    apellido: string;
    dni: string;
}