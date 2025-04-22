import { Component, OnInit } from '@angular/core';
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
import { Router, RouterLink } from '@angular/router';
import { AnimalService } from '../services/animal.service';
import { AuthService } from '../services/auth.service';
import { MatRadioModule } from '@angular/material/radio';
import { Domicilio } from '../model/Domicilio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { Veterinaria } from '../model/Veterinaria';
import { Veterinario } from '../model/Veterinario';
import { DomicilioService } from '../services/Domicilio.service';
import { VeterinariesService } from '../services/Veterinaries-service';
import { TurnoService } from '../services/Turno.service';
import { DisponibilidadRq } from '../model/DisponibilidadRq';
import { Horario } from '../model/Horario';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { D } from '@angular/cdk/keycodes';
import { VeterinarioXciudad } from '../model/veterinarioXciudad';
import { VeterinariaXciudad } from '../model/veterinariaXciudad';
import { HorarioDisponibilidad } from '../model/HorarioDisponibilidad';
import { ReservarTurnoRq } from '../model/TurnoRq';

@Component({
  selector: 'app-adm-reservar-turno',
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
    MatRadioModule,
    MatCheckboxModule,
    RouterLink
  ],
  templateUrl: './adm-reservar-turno.component.html',
  styleUrl: './adm-reservar-turno.component.scss'
})
export class AdmReservarTurnoComponent implements OnInit {

  turnoReservado: boolean = false;

  animationDuration = '1000';

  mascota: FormGroup;
  domicilio: FormGroup;
  veterinaries: FormGroup;
  turnero: FormGroup;

  domUsuario: Domicilio[];

  mascotas: Animal[] = [];

  veterinarias: VeterinariaXciudad[];

  empleadosVete: Veterinario[];

  veterinarios: VeterinarioXciudad[];

  horarios: HorarioDisponibilidad[] = [];
  idVeterinaria: number=0;


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private location: Location,
    private animalService: AnimalService,
    private veterinariesService: VeterinariesService,
    private domicilioService: DomicilioService,
    private turnoService: TurnoService,
    private dialog: MatDialog
  ){

    this.mascota = this.fb.group({
      nombreMascota:    new FormControl('', Validators.required) //checkbox de mascotas
    });

    this.domicilio = this.fb.group({
      esADomicilio:    new FormControl(''), //radio button para atencion a domicilio o no
      domicilioUsuario:     new FormControl('') //domicilios que el usuario selecciona
    })

    this.veterinaries = this.fb.group({
      vetes:               new FormControl('', Validators.required),
      veterinario:         new FormControl('', Validators.required),
      veterinariaSeleccionada: new FormControl()
      
    });

    this.turnero = this.fb.group({
      fecha:               new FormControl('', Validators.required),
      hora:             new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      console.log('getUS')
      this.getAnimales(usuario.cuil);
      this.getDomicilios(usuario.cuil);
      // this.getAnimales(usuario.cuil);
      // this.getDomicilios(usuario.cuil);
    });
  }

  getAnimales(cuil: string){
    this.animalService.getAnimales(cuil).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.mascotas = data.animales;
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: data.mensaje,
                acceptText: 'Cerrar',
                onAccept: () => {
                  this.location.back();
                }
              }
            });
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error al recuperar tus mascotas",
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
        console.log(error);
      }
    });
  }

  getDomicilios(cuil: string){
    this.domUsuario = [];
    this.domicilioService.getDoms(cuil).subscribe({
      next:(data)=> {
          if(data){
            this.domUsuario = data;
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: "Ocurrio un error al recuperar tus domicilios",
                acceptText: 'Cerrar',
                onAccept: () => {
                  this.location.back();
                }
              }
            });
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error al recuperar tus domicilios",
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
        console.log(error);
      }
    });
  }

  getVeterinaries(idCiudad: number){
    this.veterinariesService.getAll(idCiudad,this.mascota.get("nombreMascota")?.value.id,this.domicilio.get('esADomicilio')?.value?true:false).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.veterinarios = data.veterinariosIndependientes;
            this.veterinarias = data.veterinarias
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: data.mensaje,
                acceptText: 'Cerrar',
                onAccept: () => {
                  this.location.back();
                }
              }
            });
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error al recuperar los veterinarios de la Zona",
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
        console.log(error);
      }
    });
  }

  obtenerFechaFormateada(): string {
    const fecha: Date = this.turnero.get('fecha')?.value;
    if (fecha) {
      // Formatear a DD/MM/YYYY
      const dia = fecha.getDate().toString().padStart(2, '0');
      const mes = (fecha.getMonth() + 1).toString().padStart(2, '0'); // Los meses empiezan desde 0
      const anio = fecha.getFullYear();
      return `${anio}-${mes}-${dia}`;
    }
    return '';
  }

  getHorariosDisponibles(){
    let rq: DisponibilidadRq = new DisponibilidadRq();
    rq.fecha = this.obtenerFechaFormateada();
    rq.veterinarioId = this.veterinaries.get('veterinario')?.value;
    rq.veterinariaId = this.idVeterinaria;
    console.log("Require de disponibilidad: ",rq);
    this.turnoService.disponibilidad(rq).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.horarios = data.horariosDisponibles;
            console.log('Horarios: ',this.horarios)
          } else {
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
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'No fue posible recuperar los horarios',
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getVeterinariosDeVeterinaria(id: number): VeterinarioXciudad[] {
    this.idVeterinaria = id;
    const veterinaria = this.veterinarias?.find(v => v.id === id);
    if (veterinaria && Array.isArray(veterinaria.veterinarios)) {
      return veterinaria.veterinarios;
    }
    return [];
  }

  onDialogConfirmarTurno(){
    const fecha: Date = this.turnero.get('fecha')?.value;
    const dia = String(fecha.getDate()).padStart(2, '0');
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const anio = fecha.getFullYear();
    const fechaFormateada = `${dia}/${mes}/${anio}`;

    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Reservar turno',
        body: '¿Está seguro de que desea reservar un turno el día: ' + fechaFormateada + 'a las ' + this.turnero.get('hora')?.value.horaInicio + 'hs, para ' + this.mascota.get('nombreMascota')?.value.nombre + '?' ,
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.confirmarTurno()
        }
      }
    });
  }

  confirmarTurno(){
    let rq: ReservarTurnoRq = this.getObject();
    this.turnoService.reservar(rq).subscribe({
      next:(data)=>{
        if(data.estado!='ERROR'){
          this.turnoReservado = true;
        }else{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body:data.mensaje,
              cancelText: 'Cerrar'
            }
          });
        }
      }, error:(error)=>{
        console.log(error);
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'Ocurrio un error al reservar el turno',
            cancelText: 'Cerrar',
          }
        });
      }
    });
    
  }

  getObject(): ReservarTurnoRq{
    const fecha: Date = this.turnero.get('fecha')?.value;
    const dia = String(fecha.getDate()).padStart(2, '0');
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const anio = fecha.getFullYear();
    const fechaFormateada = `${anio}-${mes}-${dia}`;

    let req: ReservarTurnoRq = new ReservarTurnoRq();
    
    req.esDomicilio=this.domicilio.get('esADomicilio')?.value?true:false;
    req.fecha=fechaFormateada+" "+this.turnero.get('hora')?.value.horaInicio;
    req.animalId=this.mascota.get('nombreMascota')?.value.id;
    req.veterinarioId=this.veterinaries.get('veterinario')?.value;
    req.veterinariaId = this.idVeterinaria;

    return req;
  }


  volver(){
    this.location.back();
  }

  limpiarCamposVeterinaries(){
    this.veterinaries.get('veterinario')?.setValue(null);
    this.veterinaries.get('veterinariaSeleccionada')?.setValue(null);
    this.idVeterinaria=0;
  }

}
