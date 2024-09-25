import { MenuItems } from "../MenuItems";

export const menuItems: MenuItems[]=[
    {id:0, nombre: 'Inicio',            icono:'../assets/iconos-menu-lateral/home.svg',             path: 'home',                   hidden:false, tipo: 'accent'},
    {id:1, nombre: 'Reservar Turno',    icono:'../assets/iconos-menu-lateral/calendar_add_on.svg',  path: 'adm-reservar-turno',     hidden:false, tipo: 'accent'},
    {id:2, nombre: 'Turnos Reservados', icono:'../assets/iconos-menu-lateral/calendar_clock.svg',   path: 'adm-turnos-reservados',  hidden:false, tipo: 'accent'},
    {id:3, nombre: 'Ver Mis Mascotas',  icono:'../assets/iconos-menu-lateral/pet_supplies.svg',     path: 'mascota/1',              hidden:false, tipo: 'accent'},
    {id:4, nombre: 'Emergencia',        icono:'../assets/iconos-menu-lateral/e911_emergency.svg',   path: 'emergencia',             hidden:false, tipo: 'warn'},
  ]