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
                            nombre: 'Chico',
                            descripcion: 'Animales pequeños'
                        }
                    },
                }
            }]
        },
        veterinaria: {},
        veterinario: {},
        esADomicilio: false,
        duracionEstimada: '30 min',
        descripcion: 'Turno de vacunacion para un gato mediano',
        monto: 1500,
        esGuardia: false
    }
  ]