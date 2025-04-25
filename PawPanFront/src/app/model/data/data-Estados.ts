import { Estado } from "../Estado";

export const estados: Estado[] = [
    {
        id:1,
        ambito: "turno",
        nombre: "RESERVADO",
        descripcion: "PENDIENTE ACEPTACION",
    },
    {
        id:2,
        ambito: "turno",
        nombre: "CANCELADO",
        descripcion: "CANCELADO",
    },
    {
        id:3,
        ambito: "turno",
        nombre: "ACEPTADO",
        descripcion: "PENDIENTE DE ATENCION",
    },
    {
        id:4,
        ambito: "turno",
        nombre: "RECHAZADO",
        descripcion: "RECHAZADO",
    },
    {
        id:5,
        ambito: "turno",
        nombre: "ATENDIDO",
        descripcion: "FINALIZADO",
    },
    {
        id: null,
        ambito: "turno",
        nombre: "",
        descripcion: "TODOS LOS ESTADOS",
    },
]