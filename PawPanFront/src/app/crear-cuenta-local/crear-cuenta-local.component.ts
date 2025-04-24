import { Component, OnInit, signal } from '@angular/core';
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
import { validacionContraseniasIguales } from '../validators/validacionContraseniaIguales';
import { validacionFormatoCorreo } from '../validators/validarCorreo';
import { validacionDni } from '../validators/validacionDni';
import { validacionTelefonoBasico } from '../validators/numeroTelefono';
import { VeterinariesService } from '../services/Veterinaries-service';
import { DiaHorarioAtencion } from '../model/DiaHorarioAtencion';
import { Ciudad } from '../model/Ciudad';
import { CiudadService } from '../services/ciudad.service';
import { UsuarioRequest } from '../model/UsuarioRq';
import { DomicilioRq } from '../model/DomicilioRq';
import { UsuarioService } from '../services/usuario.service';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { MatDialog } from '@angular/material/dialog';

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
export class CrearCuentaLocalComponent implements OnInit {
  animationDuration = '1000'
  
  datosLocal: FormGroup;
  ubicacion: FormGroup;
  horarioTrabajo: FormGroup;

  usCreado: boolean=false;

  horarios: Horario[] = [];
  diasHorarios: DiaHorarioAtencion[] = [];

  ciudades: Ciudad[] = [];

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
    private location: Location,
    private service: VeterinariesService,
    private ciudadService: CiudadService,
    private usuarioService: UsuarioService,
    private dialog: MatDialog
  ){
    //console.log("Array de Horarios vacio: ",this.horarios);
    this.datosLocal = this.fb.group({
      razonSocial:  new FormControl('', Validators.required),
      cuit:         new FormControl('', [Validators.required,validacionDni('cuit')]),
      correo:       new FormControl('', [Validators.required,Validators.email,validacionFormatoCorreo]),
      telefono:     new FormControl('',[Validators.required,validacionTelefonoBasico]),
      contrasenia:          new FormControl('', [Validators.required, Validators.minLength(6)]),
      validarContrasenia:   new FormControl('', [Validators.required, Validators.minLength(6)])
    }, { validators: validacionContraseniasIguales });

    this.horarioTrabajo = this.fb.group({
      haceGuardia: new FormControl('NO', Validators.required),
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
      localFisico: new FormControl('', Validators.required),
      ciudad:    new FormControl('',Validators.required),
      calle:     new FormControl('', Validators.required),
      numero:    new FormControl('', Validators.required)
    })
  }

  ngOnInit(): void {
    this.getCiudades();
  }

  getCiudades(){
    this.ciudadService.getAll().subscribe({
      next: (data) => {
        console.log('data: ', data);
        if (data.estado != 'ERROR'){
          this.ciudades = data.ciudades;
        }  else {
          console.log(data.mensaje);
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: data.mensaje,
              cancelText: 'Cerrar'
            }
          });
        }
      }, error: (error)=>{
        console.log(error);
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un errror interno al buscar las ciudades",
            cancelText: 'Cerrar'
          }
        });
      }
    });
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
 
  onConfirmar(){
    let rq: UsuarioRequest = this.getObjet();
    console.log(rq);
    this.usuarioService.crearCuenta(rq).subscribe({
      next:(value)=> {
        if(value.estado!="ERROR"){
          this.usCreado=true
        } else{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: value.mensaje,
              cancelText: 'Cerrar',
            }
          });

        }
          console.log(value);
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error interno al crear la cuenta",
            cancelText: 'Cerrar',
          }
        });
        console.log(error);
      }
    });
  }

  tieneLocalFisico():boolean{
    // console.log(this.ubicacion.get('localFisico')?.value)
    return this.ubicacion.get('localFisico')?.value == 'SI';
  }

  getObjet(): UsuarioRequest {
    let request: UsuarioRequest = new UsuarioRequest();
      request.tipoUsuario= 'VETERINARIA'; 
      request.telefono= this.datosLocal.get('telefono')?.value;
      request.correo= this.datosLocal.get('correo')?.value;
      request.contrasenia= this.datosLocal.get('contrasenia')?.value;

      request.razonSocial= this.datosLocal.get('razonSocial')?.value;
      request.cuit= this.datosLocal.get('cuit')?.value;
      
      
      request.haceGuardia= this.horarioTrabajo.get('haceGuardia')?.value === 'SI';
      request.aptoCirugia= false; 
      request.horario= this.diasHorarios;

      if(this.ubicacion.get('localFisico')?.value==="SI"){ //si tiene local fisico entonces no trabajan a domicilio
        request.localFisico = true;
        request.haceDomicilio= false; 
      } else { //sin local fisico solo atiende a domicilio
        request.localFisico = false;
        request.haceDomicilio= true; 
      }

      //hagan o no a domicilio necesitamos saber de que ciudad son para poder ofrecer el servicio
      let dom: DomicilioRq = new DomicilioRq();
      dom.ciudadId = this.ubicacion.get('ciudad')?.value
      dom.calle = this.ubicacion.get('calle')?.value
      dom.numero= this.ubicacion.get('numero')?.value
      dom.usuario = this.datosLocal.get('cuit')?.value;
      request.domicilio=dom;
  
    return request;
  }

  volver(){
    this.location.back();
  }
}
