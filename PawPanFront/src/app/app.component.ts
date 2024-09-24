import { ChangeDetectorRef, Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import {MatListModule} from '@angular/material/list';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MediaMatcher } from '@angular/cdk/layout';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink ,MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnDestroy, OnInit{
  mobileQuery: MediaQueryList;
  
  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);
  
  menuItems: {nombre:string, icono: string, path: string, hidden:boolean}[];
  

  fillerContent = Array.from(
    {length: 10},
    () =>
      `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`,
  );

  private _mobileQueryListener: () => void;

  constructor() {
    const changeDetectorRef = inject(ChangeDetectorRef);
    const media = inject(MediaMatcher);

    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.menuItems=[
      {nombre: 'Inicio', icono:'../assets/iconos-menu-lateral/home.svg', path: 'home', hidden:false},
      {nombre: 'Reservar Turno', icono:'../assets/iconos-menu-lateral/calendar_add_on.svg', path: 'adm-reservar-turno', hidden:false},
      {nombre: 'Turnos Reservados', icono:'../assets/iconos-menu-lateral/calendar_clock.svg', path: 'adm-turnos-reservados', hidden:false},
      {nombre: 'Ver Mis Mascotas', icono:'../assets/iconos-menu-lateral/pet_supplies.svg', path: 'mascota/1', hidden:false},
      {nombre: 'Emergencia', icono:'../assets/iconos-menu-lateral/e911_emergency.svg', path: 'emergencia', hidden:false},
    ]
      
  }

  
  shouldRun = true;
}
