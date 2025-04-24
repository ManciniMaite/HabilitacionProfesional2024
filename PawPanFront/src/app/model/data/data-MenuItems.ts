import { MenuItems } from "../MenuItems";

export const menuItems: MenuItems[]=[
    {id:0,  nombre: 'Inicio',                    icono:'../assets/iconos-menu-lateral/home.svg',             path: 'home',                   hidden:false, tipo: 'accent', role:['PACIENTE','VETERINARIO','VETERINARIA']},
    {id:1,  nombre: 'Reservar Turno',            icono:'../assets/iconos-menu-lateral/calendar_add_on.svg',  path: 'adm-reservar-turno',     hidden:false, tipo: 'accent', role:['PACIENTE']},
    {id:2,  nombre: 'Administracion de turnos',  icono:'../assets/iconos-menu-lateral/event.svg',            path: 'adm-turnos-veterinario', hidden:false, tipo: 'accent', role:['VETERINARIO']},
    {id:3,  nombre: 'Administracion de turnos',  icono:'../assets/iconos-menu-lateral/event.svg',            path: 'adm-turnos-veterinaria', hidden:false, tipo: 'accent', role:['VETERINARIA']},
    {id:4,  nombre: 'Mis turnos',                icono:'../assets/iconos-menu-lateral/event.svg',            path: 'adm-turnos-cliente',     hidden:false, tipo: 'accent', role:['PACIENTE']},
    {id:5,  nombre: 'Mis Mascotas',              icono:'../assets/iconos-menu-lateral/pet_supplies.svg',     path: 'adm-mascotas',           hidden:false, tipo: 'accent', role:['PACIENTE']},
    //{id:6,  nombre: 'Administar horarios',       icono:'../assets/iconos-menu-lateral/calendar_clock.svg',   path: '',                       hidden:false, tipo: 'accent', role:['VETERINARIO','VETERINARIA']}, //OJOO solo si es Independiente
    {id:7,  nombre: 'Administrar veterinarios',  icono:'../assets/iconos-menu-lateral/adm-vaterinarios.svg', path: 'adm-veterinarios',       hidden:false, tipo: 'accent', role:['VETERINARIA']},
    {id:8,  nombre: 'Mis domicilios',            icono:'../assets/iconos-menu-lateral/home.svg',             path: 'adm-domicilios',         hidden:false, tipo: 'accent', role:['PACIENTE']},
    {id:9,  nombre: 'Emergencia',                icono:'../assets/iconos-menu-lateral/e911_emergency.svg',   path: 'emergencia',             hidden:false, tipo: 'warn',   role:['PACIENTE']},
  ]