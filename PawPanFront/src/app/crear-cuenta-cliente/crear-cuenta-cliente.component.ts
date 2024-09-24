import { Component } from '@angular/core';
import {MatStepperModule} from '@angular/material/stepper';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-crear-cuenta-cliente',
  standalone: true,
  imports: [
    MatStepperModule,
    MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatOptionModule,
    MatSelectModule
  ],
  templateUrl: './crear-cuenta-cliente.component.html',
  styleUrl: './crear-cuenta-cliente.component.scss'
})
export class CrearCuentaClienteComponent {
  animationDuration = '1000'

  datosPersonales: FormGroup;
  mascotas: FormGroup;
  ubicacion: FormGroup;

  constructor(
    private fb: FormBuilder
  ){
    this.datosPersonales = this.fb.group({
      nombre:     new FormControl('', Validators.required),
      apellido:   new FormControl('', Validators.required),
      fechaNac:   new FormControl('',Validators.required),
      dni:        new FormControl('', Validators.required),
      correo:     new FormControl('', [Validators.required, Validators.email]),
      telefono:   new FormControl('', Validators.required)
    });

    this.mascotas = this.fb.group({
      nombreMascota:    new FormControl('', Validators.required),
      foto:             new FormControl(''),
      fechaNacMascota:  new FormControl(''),
      especie:          new FormControl(''),
      peso:             new FormControl('')
    });

    this.ubicacion = this.fb.group({
      ciudad:    new FormControl(''),
      calle:     new FormControl(''),
      numero:    new FormControl('')
    })
  }
 
  onConfirmar(){
    console.log("Cuenta creada! datos:");
    console.log(this.datosPersonales.value);
    console.log(this.mascotas.value);
    console.log(this.ubicacion.value);
  }

}
