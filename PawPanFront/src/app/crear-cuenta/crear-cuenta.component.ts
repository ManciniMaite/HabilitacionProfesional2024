import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import { Router, RouterLink } from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-crear-cuenta',
  standalone: true,
  imports: [MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule ],
  templateUrl: './crear-cuenta.component.html',
  styleUrl: './crear-cuenta.component.scss'
})
export class CrearCuentaComponent {
  constructor(
    private route: Router
  ){}
  cuentaPaciente(){
    this.route.navigate(['crear-cuenta-paciente']);
  }
  cuentaVeterinario(){
    this.route.navigate(['crear-cuenta-veterinario']);
  }
  cuentaLocal(){
    this.route.navigate(['crear-cuenta-local']);
  }
}
