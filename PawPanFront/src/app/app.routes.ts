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

export const routes: Routes = [

    { path: 'adm-horarios', component: AdmHorariosComponent },
    { path: 'adm-mascotas', component: AdmMascotasComponent },
    { path: 'adm-turnos-perdientes', component: AdmTurnosPendientesComponent },
    { path: 'adm-turnos-reservados', component: AdmTurnosReservadosComponent },
    { path: 'adm-reservar-turno', component: AdmReservarTurnoComponent},
    { path: 'adm-veterinarios', component: AdmVeterinariosComponent },
    { path: 'crear-cuenta', component: CrearCuentaComponent },
    { path: 'mascota/:id', component: MascotaEditComponent },
    { path: 'home', component: InicioComponent  },
    { path: 'crear-cuenta-paciente', component: CrearCuentaClienteComponent},
    { path: 'crear-cuenta-veterinario', component: CrearCuentaVeterinarioComponent},
    { path: 'crear-cuenta-local', component: CrearCuentaLocalComponent},
    { path: 'emergencia', component: EmergenciaComponent},
    
    { path: '**', redirectTo: 'home' }
    
];