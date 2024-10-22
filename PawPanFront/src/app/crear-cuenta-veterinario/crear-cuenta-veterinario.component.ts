import { CommonModule, Location } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatStepperModule } from '@angular/material/stepper';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { Week } from '../crear-cuenta-local/crear-cuenta-local.component';
import { Horario } from '../model/Horario';

@Component({
  selector: 'app-crear-cuenta-veterinario',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatStepperModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatChipsModule,
    MatRadioModule,
    MatCheckboxModule,
    NgxMaterialTimepickerModule,
  ],
  templateUrl: './crear-cuenta-veterinario.component.html',
  styleUrl: './crear-cuenta-veterinario.component.scss'
})
export class CrearCuentaVeterinarioComponent {
  animationDuration = '1000';

  datosPersonales: FormGroup;
  matricula: FormGroup;
  formaTrabajo: FormGroup;
  horarioTrabajo: FormGroup;
  horarios: Horario[];

  readonly semana = signal<Week>(
    {
      completado: false,
      dias: [
        {nombre: "Lunes", seleccionado: false},
        {nombre: "Martes", seleccionado: false},
        {nombre: "Miercoles", seleccionado: false},
        {nombre: "jueves", seleccionado: false},
        {nombre: "Viernes", seleccionado: false},
        {nombre: "Sabado", seleccionado: false},
        {nombre: "Domingo", seleccionado: false}
      ]
    }
  )

  constructor(
    private fb: FormBuilder,
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

    this.matricula = this.fb.group({
      numeroMatricula: new FormControl('', Validators.required)
    });

    this.formaTrabajo = this.fb.group({
      independiente: new FormControl('', Validators.required)
    });

    this.horarioTrabajo = this.fb.group({
      dia:              new FormControl('', Validators.required),
      corrido:          new FormControl(''),
      horarioApertura:  new FormControl(''),
      horarioCierre:    new FormControl(''),
      mañanaInicio:    new FormControl(''),
      mañanaFin:    new FormControl(''),
      tardeInicio:    new FormControl(''),
      tardeFin:    new FormControl(''),
    });
  }

  agregarHorario(){
    let selectedHorario = new Horario;
    selectedHorario.horaInicio = this.horarioTrabajo.value.horarioApertura;
    selectedHorario.horaFin = this.horarioTrabajo.value.horarioCierre;
    if (this.horarios == undefined) {
      this.horarios = [selectedHorario];
    } else {
      this.horarios.push(selectedHorario);
    }
  }

  quitarHorario(item: Horario){
    let index
    if (this.horarios.includes(item)){
      index = this.horarios.indexOf(item);
      this.horarios.splice(index, 1);
    }
  }

  onConfirmar(){
    console.log("Cuenta creada! datos:");
    console.log(this.datosPersonales.value);
    console.log(this.matricula.value);
    console.log(this.formaTrabajo.value);
    console.log(this.horarioTrabajo.value);
  }

  volver(){
    this.location.back();
  }
}
