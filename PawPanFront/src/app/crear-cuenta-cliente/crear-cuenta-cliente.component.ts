import { Component } from '@angular/core';
import { MatStepperModule } from '@angular/material/stepper';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule, Location } from '@angular/common'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { Animal } from '../model/Animal';
import { MatChipsModule } from '@angular/material/chips';
import { Router } from '@angular/router';

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
    MatSelectModule,
    MatChipsModule
  ],
  templateUrl: './crear-cuenta-cliente.component.html',
  styleUrl: './crear-cuenta-cliente.component.scss'
})
export class CrearCuentaClienteComponent {
  animationDuration = '1000'

  datosPersonales: FormGroup;
  mascota: FormGroup;
  ubicacion: FormGroup;

  animales: Animal[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private location: Location
  ){
    this.datosPersonales = this.fb.group({
      nombre:     new FormControl('', Validators.required),
      apellido:   new FormControl('', Validators.required),
      fechaNac:   new FormControl('', Validators.required),
      dni:        new FormControl('', Validators.required),
      correo:     new FormControl('', [Validators.required, Validators.email]),
      telefono:   new FormControl('', Validators.required)
    });

    this.mascota = this.fb.group({
      nombreMascota:    new FormControl('', Validators.required),
      foto:             new FormControl(''),
      fechaNacMascota:  new FormControl(''),
      especie:          new FormControl(''),
      raza:             new FormControl(''),
      peso:             new FormControl('')
    });

    this.ubicacion = this.fb.group({
      ciudad:    new FormControl(''),
      calle:     new FormControl(''),
      numero:    new FormControl('')
    })
  }

  agregarAnimal(){
    let selectedAnimal = new Animal;
    selectedAnimal.nombre = this.mascota.value.nombreMascota;
    selectedAnimal.fechaNac = this.mascota.value.fechaNacMascota;
    selectedAnimal.raza.especie = this.mascota.value.especie;
    selectedAnimal.raza = this.mascota.value.raza;
    selectedAnimal.peso = this.mascota.value.peso;
    this.animales.push(selectedAnimal);
    console.log("animal seleccionado: ", selectedAnimal);
    console.log("array de animales: ", this.animales);
  }

  quitarAnimal(item: Animal){
    let index
    if (this.animales.includes(item)){
      index = this.animales.indexOf(item);
      this.animales.splice(index, 1);
    }
  }
 
  onConfirmar(){
    console.log("Cuenta creada! datos:");
    console.log(this.datosPersonales.value);
    console.log(this.mascota.value);
    console.log(this.ubicacion.value);
  }

  volver(){
    this.location.back();
  }

  // mostrarFecha(){
  //   const fecha = this.datosPersonales.get('fechaNac')?.value;

  //   // Formato para la fecha: dd/MM/yyyy
  //   const opcionesFecha: Intl.DateTimeFormatOptions = { day: '2-digit', month: '2-digit', year: 'numeric' };
  //   const fechaFormateada = fecha.toLocaleDateString('es-AR', opcionesFecha);

  //   // Nombre completo del día
  //   const opcionesDia: Intl.DateTimeFormatOptions = { weekday: 'long' };
  //   const diaSemana = fecha.toLocaleDateString('es-AR', opcionesDia);

  //   console.log(`Día: ${diaSemana}, Fecha: ${fechaFormateada}`);
  //   console.log('fecha: ', this.datosPersonales.get('fechaNac')?.value)
  // }

}
