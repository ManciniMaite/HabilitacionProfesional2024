import { Turno } from "../Turno";

export const TURNOBYID: Turno = {
    "id": 6,
    "fechaHoraReserva": "2025-04-22T20:32:56.810+00:00",
    "fechaHora": "2025-04-29T09:00:00.000+00:00",
    "estado": {
        "id": 1,
        "ambito": "turno",
        "nombre": "RESERVADO",
        "descripcion": ""
    },
    "animal": {
        "id": 1,
        "esActivo": true,
        "nombre": "Nailon",
        "fechaNac": "2024-11-21T03:00:00.000+00:00",
        "peso": 5.0,
        "raza": {
            "id": 25,
            "nombre": "Sin especificar",
            "especie": {
                "id": 1,
                "nombre": "perro",
                "tipoEspecie": {
                    "id": 1,
                    "nombre": "Animales pequeños",
                    "descripcion": undefined
                }
            }
        },
        "cliente": {
            "id": 9,
            "telefono": "3534015350",
            "role": "PACIENTE",
            "correo": "celesteperez9926@gmail.com",
            "contrasenia": "$2a$10$JLVJTK2GBJdwkxcTvFCQrO.sr3pm4zQMIzaN2s19OqJ2c5wIfFs.G",
            "nombre": "Celeste",
            "apellido": "Perez",
            "dni": "41589625",
            "activo": true
        }
    },
    "veterinaria": undefined,
    "veterinario": {
        "id": 11,
        "telefono": "3536248625",
        "role": "VETERINARIO",
        "correo": "RiveroM@gmail.com",
        "contrasenia": "$2a$10$HEsQPqCrksdgrFmVNIwpaujBKH7kwT2pfO4IUxRXmq/oWji79d2Ce",
        "nombre": "Marcela",
        "apellido": "Rivero",
        "dni": "25920771",
        "fechaNac": "1990-12-28T02:00:00.000Z",
        "matricula": "2227",
        "esIndependiente": true,
        "haceGuardia": false,
        "horarios": undefined,
        "haceDomicilio": true,
        "veterinaria": undefined,
        "tiposEspecie": [
            {
                "id": 1,
                "nombre": "Animales pequeños",
                "descripcion": undefined
            }
        ],
        "activo": true
    },
    "esADomicilio": true,
    "descripcionPublica": null,
    "descripcionPrivada": null
}