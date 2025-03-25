import { Turno } from "../Turno";

export const Turnos: Turno[]=[
    {
        fechaHoraReserva: '09/10 09:00',
        fecha: '09/10/24',
        hora: '09:00 AM',
        detalleTurno: [{
            monto: 5,
            tipoConsulta: {
                monto: 1500,
                nombre: 'Vacunación',
                duracion: '30 min',
                descripcion: 'Vacunacion de un solo animal'
            },
            cantidad: 1,
            descripcion: 'vacunacion para la mascota',
            animal: {
                esActivo: true,
                nombre: 'Agatha',
                fechaNac: '05/05/12',
                peso: 4.5,
                foto: 'Foto del animal',
                raza: {
                    id:1,
                    nombre: 'Persa',
                    especie: {
                        id:1,
                        nombre: 'Gato',
                        tipoEspecie: {
                            id:1,
                            nombre: 'Chico',
                            descripcion: 'Animales pequeños'
                        }
                    },
                }
            }
        }],
        estado: {
            ambito: 'Ámbito',
            nombre: 'Pendiente',
            descripcion: 'Turno pendiente de atenderse'
        },
        cliente: {
            nombre: 'Don Jorge',
            apellido: 'Perez',
            dni: '43568974',
            animales: [{
                esActivo: true,
                nombre: 'Agatha',
                fechaNac: '05/05/12',
                peso: 4.5,
                foto: 'Foto del animal',
                raza: {
                    id:1,
                    nombre: 'Persa',
                    especie: {
                        id:1,
                        nombre: 'Gato',
                        tipoEspecie: {
                            id:1,
                            nombre: 'Chico',
                            descripcion: 'Animales pequeños'
                        }
                    },
                }
            }]
        },
        veterinaria: {
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
        },
        veterinario: {
            id: 0,
            telefono: '0353564789',
            role: {
                role: 'admin',
                descripcion: 'rol administrador'
            },
            correo: 'dardo@gmail.com',
            contrasenia: 'asdasdasdasdasd',
            isActivo: true,
            nombre: 'Dardo',
            apellido: 'Fuseneco',
            dni: '41567892',
            fechaNac: '05/05/1998',
            matricula: 1234567890,
            esIndependiente: true,
            haceGuardia: false,
            horario: [{
                id: 0,
                dia: 'lunes',
                horario: {
                    id: 0,
                    horaInicio: '07:30',
                    horaFin: '15:30'
                }
            }],
            especialidad: [{
                id: 0,
                nombre: 'Animales pequeños'
            }],
            haceDomicilio: true
        },
        esADomicilio: false,
        duracionEstimada: '30 min',
        descripcion: 'Turno de vacunacion para un gato mediano',
        monto: 1500,
        esGuardia: false
    }
  ]