import { Veterinaria } from "../Veterinaria";

export const veterinariaMock: Veterinaria[]= [{
    id: 0,
    telefono: '0353564875',
    role: {
        role: 'admin',
        descripcion: 'rol administrador'
    },
    correo: 'cahorros.sa@gmail.com',
    contrasenia: 'qweqweqweqweqwe',
    isActivo: true,
    razonSocial: 'Cachorros SA',
    cuit: '2045678932',
    haceGuardia: true,
    aptoCirugia: false,
    horarioAtencion: [{
        id: 0,
        dia: 'Lunes',
        horario: {
            id: 0,
            horaInicio: '08:00',
            horaFin: '16:00'
        }
    }],
    veterinarios: [{
        id: 0,
        telefono: '0353564789',
        role: {
            role: 'admin',
            descripcion: 'rol administrador'
        },
        correo: 'dardo@gmail.com',
        contrasenia: 'asdasdasdasdasd',
        isActivo: true,
        nombre: 'Juan',
        apellido: 'Carlos',
        dni: '41234967',
        fechaNac: '13/11/1985',
        matricula: 1234987056,
        esIndependiente: false,
        haceGuardia: true,
        horario: [{
            id: 0,
            dia: 'lunes',
            horario: {
                id: 0,
                horaInicio: '08:00',
                horaFin: '16:00'
            }
        }],
        especialidad: [{
            id: 0,
            nombre: 'Animales grandes'
        }],
        haceDomicilio: true
    }],
    haceDomicilio: true
}]