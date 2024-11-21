import { Animal } from "./Animal";
import { DiaHorarioAtencion } from "./DiaHorarioAtencion";
import { Domicilio } from "./Domicilio";
import { Especialidad } from "./Especialidad";
import { Veterinario } from "./Veterinario";

export class UsuarioRq {
    //COMUNES
    tipoUsuario: string;
    telefono: string;
    correo: string;
    contrasenia: string;
    
//PACIENTE - VETERINARIO
    nombre: string;
    apellido: string;
    dni: string;
    fechaNac: string;
    
//PACIENTE
   animales: Animal[];
    
//VETERINARIA
    razonSocial: string;
    cuit: string;
    veterinarios: Veterinario[];

//VETERINARIA - VETERINARIO
    haceGuardia: boolean;
    aptoCirugia: boolean;
    horarioAtencion: DiaHorarioAtencion[];
    horario: DiaHorarioAtencion[];
    haceDomicilio: boolean;

//VETERINARIO
    matricula: number;
    esIndependiente: boolean;
    especialidad:Especialidad[];
    
//VETERINARIA - PACIENTE
    domicilio: Domicilio;
}