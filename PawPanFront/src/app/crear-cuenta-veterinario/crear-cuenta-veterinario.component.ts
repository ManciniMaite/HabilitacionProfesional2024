import { CommonModule, Location } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
import { validacionContraseniasIguales } from '../validators/validacionContraseniaIguales';
import { validacionFormatoCorreo } from '../validators/validarCorreo';
import { validacionTelefonoBasico } from '../validators/numeroTelefono';
import { validacionDni } from '../validators/validacionDni';
import { validacionFecha } from '../validators/validacionFecha';
import { VeterinariesService } from '../services/Veterinaries-service';
import { ValidarMatriculaRq } from '../model/validarMatriculaRq';
import { DiaHorarioAtencion } from '../model/DiaHorarioAtencion';

@Component({
  selector: 'app-crear-cuenta-veterinario',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
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
  diasHorarios: DiaHorarioAtencion[] = [];
  horarios: Horario[];

  matriculaValidada: boolean = false;
  mensajeMatricula: string = "";

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
    private location: Location,
    private service: VeterinariesService
  ){
    this.datosPersonales = this.fb.group({
      nombre:     new FormControl('', Validators.required),
      apellido:   new FormControl('', Validators.required),
      fechaNac:   new FormControl('', [Validators.required,validacionFecha(18)]), //+22?
      dni:        new FormControl('',[Validators.required, validacionDni('dni')]),
      correo:     new FormControl('', [Validators.required, Validators.email, validacionFormatoCorreo]),
      telefono:   new FormControl('', [Validators.required, validacionTelefonoBasico]),
      contrasenia:          new FormControl('', [Validators.required, Validators.minLength(6)]),
      validarContrasenia:   new FormControl('', [Validators.required, Validators.minLength(6)])
    }, { validators: validacionContraseniasIguales });

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

  validarMatricula(){
    this.matriculaValidada = false;
    this.mensajeMatricula="Validando..."
    let rq: ValidarMatriculaRq = new ValidarMatriculaRq();
    rq.dni=this.datosPersonales.get('dni')?.value;
    rq.nroMatricula=this.matricula.get('numeroMatricula')?.value;
    this.service.validarMatricula(rq).subscribe({
      next:(data)=> {
        if(data.estado=="ERROR"){
          this.mensajeMatricula=data.mensaje;
        }  else{
          this.mensajeMatricula=data.mensaje;
          this.matriculaValidada = true;
        }
      }, error: (error)=>{
        console.log(error);
        this.mensajeMatricula="Ups... ocurrió un error al validar la matricula!";
      }
    });
  }

  validarDatosPersonales(): boolean{
    return (this.datosPersonales.get('nombre')?.value != null && this.datosPersonales.get('nombre')?.value != undefined && this.datosPersonales.get('nombre')?.value != "")&&
           (this.datosPersonales.get('apellido')?.value != null && this.datosPersonales.get('apellido')?.value != undefined && this.datosPersonales.get('apellido')?.value != "")&&
           (this.datosPersonales.get('fechaNac')?.value != null && this.datosPersonales.get('fechaNac')?.value != undefined && this.datosPersonales.get('fechaNac')?.value != "")&&
           (this.datosPersonales.get('dni')?.value != null && this.datosPersonales.get('dni')?.value != undefined && this.datosPersonales.get('dni')?.value != "")&&
           (this.datosPersonales.get('correo')?.value != null && this.datosPersonales.get('correo')?.value != undefined && this.datosPersonales.get('correo')?.value != "")&&
           (this.datosPersonales.get('telefono')?.value != null && this.datosPersonales.get('telefono')?.value != undefined && this.datosPersonales.get('telefono')?.value != "")&&
           (this.datosPersonales.get('contrasenia')?.value != null && this.datosPersonales.get('contrasenia')?.value != undefined && this.datosPersonales.get('contrasenia')?.value != "")&&
           (this.datosPersonales.get('validarContrasenia')?.value != null && this.datosPersonales.get('validarContrasenia')?.value != undefined && this.datosPersonales.get('validarContrasenia')?.value != "")&&
           this.validarContrasenias();
  }

  validarContrasenias(): boolean{
    return this.datosPersonales.get('contrasenia')?.value === this.datosPersonales.get('validarContrasenia')?.value;
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

  habilitarAgregarHorario(){
    const corrido = this.horarioTrabajo.get('corrido')?.value;
    // console.log('Corrido:', corrido);  // Verifica si el valor de 'corrido' es correcto

    const diasSeleccionados = (this.semana().dias?.filter(d => d.seleccionado).length ?? 0) > 0;
    // console.log('Días seleccionados:', diasSeleccionados);  // Verifica si al menos un día está seleccionado

    if (!diasSeleccionados) {
      // console.log('No se han seleccionado días.');
      return false;
    }

    if (corrido =='si') {
      // Si "corrido" está marcado, los campos de horario de apertura y cierre deben tener valor
      const horarioApertura = this.horarioTrabajo.get('horarioApertura')?.value;
      const horarioCierre = this.horarioTrabajo.get('horarioCierre')?.value;
      // console.log('Horario Apertura:', horarioApertura);  // Verifica el valor de 'horarioApertura'
      // console.log('Horario Cierre:', horarioCierre);      // Verifica el valor de 'horarioCierre'
      
      const isValidHorario = !!horarioApertura && !!horarioCierre;
      // console.log('Es válido el horario (corrido):', isValidHorario);  // Verifica si ambos horarios están completos
      
      return isValidHorario; // Si ambos tienen valor, habilitar el botón
    } else if(corrido == 'no') {
      // Si "corrido" no está marcado, los campos de mañana y tarde deben tener valor
      const mañanaInicio = this.horarioTrabajo.get('mañanaInicio')?.value;
      const mañanaFin = this.horarioTrabajo.get('mañanaFin')?.value;
      const tardeInicio = this.horarioTrabajo.get('tardeInicio')?.value;
      const tardeFin = this.horarioTrabajo.get('tardeFin')?.value;

      // console.log('Mañana Inicio:', mañanaInicio); // Verifica el valor de 'mañanaInicio'
      // console.log('Mañana Fin:', mañanaFin);       // Verifica el valor de 'mañanaFin'
      // console.log('Tarde Inicio:', tardeInicio);   // Verifica el valor de 'tardeInicio'
      // console.log('Tarde Fin:', tardeFin);         // Verifica el valor de 'tardeFin'

      const isValidPartesDelDia = !!mañanaInicio && !!mañanaFin && !!tardeInicio && !!tardeFin;
      // console.log('Es válido el horario (no corrido):', isValidPartesDelDia);  // Verifica si todos los campos están completos
      
      return isValidPartesDelDia; // Si todos los campos están completos, habilitar el botón
    } else{
      return false;
    }
  }
  
    
  
  agregarHorario() {
    if (this.horarioTrabajo.get('corrido')?.value == "si") {
      this.semana().dias?.forEach(element => {
        if (element.seleccionado) {
          const yaExiste = this.diasHorarios.some(dh => dh.dia === element.nombre);
          if (!yaExiste) {

            console.log('Horario service: ', this.service.crearDiaHorarioAtencionCorrido(
              element.nombre,
              this.horarioTrabajo.get('horarioApertura')?.value,
              this.horarioTrabajo.get('horarioCierre')?.value
            ))

            this.diasHorarios.push(
              this.service.crearDiaHorarioAtencionCorrido(
                element.nombre,
                this.horarioTrabajo.get('horarioApertura')?.value,
                this.horarioTrabajo.get('horarioCierre')?.value
              )
            );
          } else {
            console.log(`El día ${element.nombre} ya tiene un horario agregado.`);
          }
        }
      });
    } else if(this.horarioTrabajo.get('corrido')?.value == "no"){
      this.semana().dias?.forEach(element => {
        if (element.seleccionado) {
          const yaExiste = this.diasHorarios.some(dh => dh.dia === element.nombre);
          if (!yaExiste) {

            console.log('Dia horario del service: ', this.service.crearDiaHorarioAtencionCortado(
              element.nombre,
              this.horarioTrabajo.get('mañanaInicio')?.value,
              this.horarioTrabajo.get('mañanaFin')?.value,
              this.horarioTrabajo.get('tardeInicio')?.value,
              this.horarioTrabajo.get('tardeFin')?.value
            ))
              console.log('element.nombre: ', element.nombre);
            this.diasHorarios.push(
              this.service.crearDiaHorarioAtencionCortado(
                element.nombre,
                this.horarioTrabajo.get('mañanaInicio')?.value,
                this.horarioTrabajo.get('mañanaFin')?.value,
                this.horarioTrabajo.get('tardeInicio')?.value,
                this.horarioTrabajo.get('tardeFin')?.value
              )
            );
          } else {
            console.log(`El día ${element.nombre} ya tiene un horario agregado.`);
          }
        }
      });
    }
    this.limpiarHorario();
  }
  
  limpiarHorario(){
    this.semana.set({
      completado: false,
      dias: [
        { nombre: "Lunes", seleccionado: false },
        { nombre: "Martes", seleccionado: false },
        { nombre: "Miercoles", seleccionado: false },
        { nombre: "jueves", seleccionado: false },
        { nombre: "Viernes", seleccionado: false },
        { nombre: "Sabado", seleccionado: false },
        { nombre: "Domingo", seleccionado: false }
      ]
    });
    this.horarioTrabajo.get('horarioApertura')?.reset();
    this.horarioTrabajo.get('horarioCierre')?.reset();
    this.horarioTrabajo.get('mañanaInicio')?.reset();
    this.horarioTrabajo.get('mañanaFin')?.reset();
    this.horarioTrabajo.get('tardeInicio')?.reset();
    this.horarioTrabajo.get('tardeFin')?.reset();

  }
  
  quitarHorario(item: DiaHorarioAtencion){
    let index
    if (this.diasHorarios.includes(item)){
      index = this.diasHorarios.indexOf(item);
      this.diasHorarios.splice(index, 1);
    }
  }

  habilitarCrearCuenta(){
    if(this.formaTrabajo.get('independiente')?.value=='false'){
      return true;
    } else if(this.formaTrabajo.get('independiente')?.value=='true' && this.diasHorarios.length!=0){
      return true;
    } else{
      return false;
    }
  }

}
