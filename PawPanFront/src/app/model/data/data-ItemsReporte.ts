export class ItemReporte{
    id: number;
    nombre: string;
    descripcion: string;
    rol: string[];
    path: string; 
}

export const itemsReportes: ItemReporte[] = [
    {id:1, nombre: "Turnos por Especie",                               descripcion:"Cantidad de turnos atendidos por cada especie de animal entre dos fechas",                          rol: ['VETERINARIA','VETERINARIO'], path:"reportes/turnos-por-especie"},
    {id:2, nombre: "Turnos por Estado",                                descripcion:"Porcentaje del estado de los turnos dados entre dos fechas. Mide la eficiencia de la agenda",       rol: ['VETERINARIA','VETERINARIO'], path:"reportes/turnos-por-estado"},
    // {id:3, nombre: "Tasa de cancelacion",                              descripcion:"Porcentaje que indica la perdida de oportunidades en un rango de tiempo",                           rol: ['VETERINARIA','VETERINARIO'], path:""},
    // {id:4, nombre: "Tasa de atencion",                                 descripcion:"Porcentaje de la cantidad de turnos que se atendieron en un rango de tiempo",                       rol: ['VETERINARIA','VETERINARIO'], path:""},    
    {id:5, nombre: "Turnos por Veterinario",                           descripcion:"Mide la actividad de cada veterinario en un rango de fechas",                                       rol: ['VETERINARIA'],               path:"reportes/turnos-por-veterinario"},
    // {id:6, nombre: "Turnos Pendientes de Atencion por veterinario",    descripcion:"Mide la demanda de los veterinarios en un rango de fechas",                                         rol: ['VETERINARIA'],               path:"reportes/turnos-por-veterinario/6"},
    // {id:7, nombre: "Turnos Rechazados por Veterinario",                descripcion:"Mide el porcentaje de turnos que fueron rechazados por cada veterinario en un rango de fechas",     rol: ['VETERINARIA'],               path:"reportes/turnos-por-veterinario/7"},
]