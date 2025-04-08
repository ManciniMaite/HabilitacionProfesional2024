import { Routes } from '@angular/router';
import { AdmHorariosComponent } from './adm-horarios/adm-horarios.component';
import { AdmMascotasComponent } from './adm-mascotas/adm-mascotas.component';
import { AdmTurnosPendientesComponent } from './adm-turnos-pendientes/adm-turnos-pendientes.component';
import { AdmTurnosReservadosComponent } from './adm-turnos-reservados/adm-turnos-reservados.component';
import { AdmVeterinariosComponent } from './adm-veterinarios/adm-veterinarios.component';
import { CrearCuentaComponent } from './crear-cuenta/crear-cuenta.component';
import { InicioComponent } from './inicio/inicio.component';
import { MascotaEditComponent } from './mascota-edit/mascota-edit.component';
import { CrearCuentaClienteComponent } from './crear-cuenta-cliente/crear-cuenta-cliente.component';
import { CrearCuentaVeterinarioComponent } from './crear-cuenta-veterinario/crear-cuenta-veterinario.component';
import { CrearCuentaLocalComponent } from './crear-cuenta-local/crear-cuenta-local.component';
import { AdmReservarTurnoComponent } from './adm-reservar-turno/adm-reservar-turno.component';
import { EmergenciaComponent } from './emergencia/emergencia.component';
import { HomeComponent } from './home/home.component';
import { authGuard } from './access-guard/authGuard';

export const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'adm-horarios', component: AdmHorariosComponent, canActivate: [authGuard] },
  { path: 'adm-mascotas', component: AdmMascotasComponent, canActivate: [authGuard] },
  { path: 'adm-turnos-perdientes', component: AdmTurnosPendientesComponent, canActivate: [authGuard] },
  { path: 'adm-turnos-reservados', component: AdmTurnosReservadosComponent, canActivate: [authGuard] },
  { path: 'adm-reservar-turno', component: AdmReservarTurnoComponent, canActivate: [authGuard] },
  { path: 'adm-veterinarios', component: AdmVeterinariosComponent, canActivate: [authGuard] },
  { path: 'mascota/:id', component: MascotaEditComponent, canActivate: [authGuard] },
  { path: 'emergencia', component: EmergenciaComponent, canActivate: [authGuard] },

  // Rutas públicas
  { path: 'iniciar-sesion', component: InicioComponent },
  { path: 'crear-cuenta', component: CrearCuentaComponent },
  { path: 'crear-cuenta-paciente', component: CrearCuentaClienteComponent },
  { path: 'crear-cuenta-veterinario', component: CrearCuentaVeterinarioComponent },
  { path: 'crear-cuenta-local', component: CrearCuentaLocalComponent },

  { path: '**', redirectTo: 'iniciar-sesion' }
];