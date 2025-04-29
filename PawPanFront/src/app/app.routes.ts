import { Routes } from '@angular/router';
import { AdmHorariosComponent } from './adm-horarios/adm-horarios.component';
import { AdmMascotasComponent } from './adm-mascotas/adm-mascotas.component';
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
import { AtenderTurnoComponent } from './atender-turno/atender-turno.component';
import { AdmTurnosVeterinarioComponent } from './adm-turnos-veterinario/adm-turnos-veterinario.component';
import { AdmTurnosVeterinariaComponent } from './adm-turnos-veterinaria/adm-turnos-veterinaria.component';
import { AdmTurnosClienteComponent } from './adm-turnos-cliente/adm-turnos-cliente.component';
import { VerTurnoComponent } from './ver-turno/ver-turno.component';
import { AdmDmiciliosComponent } from './adm-dmicilios/adm-dmicilios.component';
import { NuevoTurnoComponent } from './nuevo-turno/nuevo-turno.component';
import { ReportesComponent } from './reportes/reportes.component';
import { TurnosPorVeterinarioComponent } from './reportes/turnos-por-veterinario/turnos-por-veterinario.component';
import { TurnosPorEspecieComponent } from './reportes/turnos-por-especie/turnos-por-especie.component';
import { TurnosPorEstadoComponent } from './reportes/turnos-por-estado/turnos-por-estado.component';
import { RecuperarPasswordComponent } from './recuperar-password/recuperar-password.component';

export const routes: Routes = [
  { path: 'home',                                 component: HomeComponent,                 canActivate: [authGuard] },
  { path: 'adm-horarios',                         component: AdmHorariosComponent,          canActivate: [authGuard] },
  { path: 'adm-mascotas',                         component: AdmMascotasComponent,          canActivate: [authGuard] },
  { path: 'adm-turnos-veterinario',               component: AdmTurnosVeterinarioComponent, canActivate: [authGuard] },
  { path: 'adm-turnos-veterinaria',               component: AdmTurnosVeterinariaComponent, canActivate: [authGuard] },
  { path: 'adm-turnos-cliente',                   component: AdmTurnosClienteComponent,     canActivate: [authGuard] },
  { path: 'adm-reservar-turno',                   component: AdmReservarTurnoComponent,     canActivate: [authGuard] },
  { path: 'adm-veterinarios',                     component: AdmVeterinariosComponent,      canActivate: [authGuard] },
  { path: 'mascota/:id',                          component: MascotaEditComponent,          canActivate: [authGuard] },
  { path: 'emergencia',                           component: EmergenciaComponent,           canActivate: [authGuard] },
  { path: 'atender-turno/:id',                    component: AtenderTurnoComponent,         canActivate: [authGuard] },
  { path: 'ver-turno/:id',                        component: VerTurnoComponent,             canActivate: [authGuard] },
  { path: 'adm-domicilios',                       component: AdmDmiciliosComponent,         canActivate: [authGuard] },
  { path: 'nuevo-turno',                          component: NuevoTurnoComponent,           canActivate: [authGuard] },
  { path: 'reportes',                             component: ReportesComponent,             canActivate: [authGuard] },
  { path: 'reportes/turnos-por-veterinario',      component: TurnosPorVeterinarioComponent, canActivate: [authGuard] },
  { path: 'reportes/turnos-por-especie',          component: TurnosPorEspecieComponent,     canActivate: [authGuard] },
  { path: 'reportes/turnos-por-estado',           component: TurnosPorEstadoComponent,      canActivate: [authGuard] },

  // Rutas públicas
  { path: 'iniciar-sesion',             component: InicioComponent },
  { path: 'crear-cuenta',               component: CrearCuentaComponent },
  { path: 'crear-cuenta-paciente',      component: CrearCuentaClienteComponent },
  { path: 'crear-cuenta-veterinario',   component: CrearCuentaVeterinarioComponent },
  { path: 'crear-cuenta-local',         component: CrearCuentaLocalComponent },
  { path: 'recuperar-password',         component: RecuperarPasswordComponent },

  { path: '**', redirectTo: 'iniciar-sesion' }
];