import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { Turnos } from '../model/data/data-Turnos';
import { MatExpansionModule } from '@angular/material/expansion';
import { Turno } from '../model/Turno';

@Component({
  selector: 'app-adm-turnos-reservados',
  standalone: true,
  imports: [MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    MatExpansionModule,
  ],
  templateUrl: './adm-turnos-reservados.component.html',
  styleUrl: './adm-turnos-reservados.component.scss'
})
export class AdmTurnosReservadosComponent {

  readonly panelOpenState = signal(false);

  turnos: Turno[] = Turnos;

  constructor(private routes: Router){}
}
