import { Component, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Horario } from '../model/Horario';
import { Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatStepperModule } from '@angular/material/stepper';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

export interface Week {
  completado: boolean;
  dias?: {nombre: string, seleccionado: boolean}[];
}

@Component({
  selector: 'app-crear-cuenta-local',
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
    MatChipsModule,
    MatRadioModule,
    MatCheckboxModule,
    NgxMaterialTimepickerModule,
    FormsModule
  ],
  templateUrl: './crear-cuenta-local.component.html',
  styleUrl: './crear-cuenta-local.component.scss'
})
export class CrearCuentaLocalComponent {
  animationDuration = '1000'
  
  datosLocal: FormGroup;
  ubicacion: FormGroup;
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
    private router: Router,
    private location: Location
  ){
    console.log("Array de Horarios vacio: ",this.horarios);
    this.datosLocal = this.fb.group({
      razonSocial:  new FormControl('', Validators.required),
      cuit:         new FormControl('', Validators.required),
      correo:       new FormControl('', Validators.required),
      telefono:     new FormControl('',Validators.required),
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

    this.ubicacion = this.fb.group({
      ciudad:    new FormControl(''),
      calle:     new FormControl(''),
      numero:    new FormControl('')
    })
  }

  update(completed: boolean, index?: number) {
    this.semana.update(element => {
      if (index === undefined) {
        element.completado = completed;
        element.dias?.forEach(d => (d.seleccionado = completed));
      } else {
        element.dias![index].seleccionado = completed;
        element.completado = element.dias?.every(d => d.seleccionado) ?? true;
      }
      return {...element};
    });
  }

  agregarHorario(){
    let selectedHorario = new Horario;
    selectedHorario.id = 0;
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
    console.log(this.datosLocal.value);
    console.log(this.horarioTrabajo.value);
    console.log(this.ubicacion.value);
  }

  volver(){
    this.location.back();
  }
}
