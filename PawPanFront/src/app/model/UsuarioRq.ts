import { Animal } from "./Animal";
import { AnimalRq } from "./AnimalRq";
import { DiaHorarioAtencion } from "./DiaHorarioAtencion";
import { Domicilio } from "./Domicilio";
import { DomicilioRq } from "./DomicilioRq";
import { Especialidad } from "./Especialidad";
import { Veterinario } from "./Veterinario";

export class UsuarioRequest {
    // COMUNES
    tipoUsuario: string;
    telefono: string;
    correo: string;
    contrasenia: string;

    // PACIENTE - VETERINARIO
    nombre: string;
    apellido: string;
    dni: string;
    fechaNac: string;

    // PACIENTE
    animales: AnimalRq[];

    // VETERINARIA
    razonSocial: string;
    cuit: string;
    veterinarios: Veterinario[];
    localFisico: boolean;

    // VETERINARIA - VETERINARIO
    haceGuardia: boolean;
    aptoCirugia: boolean;
    horario: DiaHorarioAtencion[];
    haceDomicilio: boolean;
    tipoEspeciesIds: number[];

    // VETERINARIO
    matricula: number;
    esIndependiente: boolean;

    // VETERINARIA - PACIENTE
    domicilio: DomicilioRq;
}