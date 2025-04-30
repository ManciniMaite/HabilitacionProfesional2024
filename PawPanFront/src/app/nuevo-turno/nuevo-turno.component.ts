import { Component, inject, OnInit } from '@angular/core';
import { ClienteDTO } from '../model/ClienteDTO';
import { TurnoService } from '../services/Turno.service';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { AnimalDTO } from '../model/AnimalDTO';
import { Animal } from '../model/Animal';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, Location } from '@angular/common';
import { MatFormField, MatSelectModule } from '@angular/material/select';
import { DateAdapter, MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { RazaService } from '../services/raza.service';
import { EspecieService } from '../services/especie.service';
import { UsuarioService } from '../services/usuario.service';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Cliente } from '../model/Cliente';
import { AnimalService } from '../services/animal.service';
import { Especie } from '../model/Especie';
import { Raza } from '../model/Raza';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { AuthService } from '../services/auth.service';
import { VeterinariesService } from '../services/Veterinaries-service';
import { ProfesionalesPorVeterinaria } from '../model/ProfesionalPorVeterinaria';
import { fechaDesdeHoyValidator } from '../validators/ValidarFechaVieja';
import { HorarioDisponibilidad } from '../model/HorarioDisponibilidad';
import { DisponibilidadRq } from '../model/DisponibilidadRq';
import { MatRadioModule } from '@angular/material/radio';
import { ReservarTurnoRq } from '../model/ReservarTurnoRq';
import { AnimalRq } from '../model/AnimalRq';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-nuevo-turno',
  standalone: true,
  imports: [
    MatAutocompleteModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatOptionModule,
    MatFormFieldModule,
    MatCardModule,
    FormsModule,
    MatInputModule,
    CommonModule,
    MatTooltipModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatRadioModule,
    RouterLink
  ],
  templateUrl: './nuevo-turno.component.html',
  styleUrl: './nuevo-turno.component.scss'
})
export class NuevoTurnoComponent implements OnInit{
  authService = inject(AuthService); 
  rol: string

  inputDNI: string
  clientes: ClienteDTO[];
  clientesFiltrados: ClienteDTO[];
  clienteSeleccionado:ClienteDTO | Cliente |null;
  animalesDTO: AnimalDTO[] 
  animales: Animal[];
  animalSeleccionado: AnimalDTO | Animal | null;

  nomApCliente: string;
  idClienteTurno: number;
  dniClienteTurno:string;
  idAnimalTurno: number;


  esClienteNuevo: boolean = false;
  banderaBuscarCliente:boolean = false;
  banderaNuevoAnimal: boolean = false;
  busquedaTurnos:boolean = false;
  esVeterinarioLogueado: boolean = false;
  turnoReservado: boolean = false;

  date: Date = new Date()


  especies: Especie[] = [];
  razas: Raza[] = [];
  mascota: FormGroup

  turnero: FormGroup;
  horarios: HorarioDisponibilidad[] = [];

  veterinarios: ProfesionalesPorVeterinaria[] = [];
  idVeterinarioTurno:number;

  constructor(
    private turnoService: TurnoService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private location: Location,
    private razasService: RazaService,
    private especieService: EspecieService,
    private usuarioService: UsuarioService,
    private animalService: AnimalService,
    private veterinarieService: VeterinariesService,
  ){
    this.mascota = this.fb.group({
      nombreMascota:    new FormControl('', Validators.required),
      fechaNacMascota:  new FormControl(''),
      especie:          new FormControl('',Validators.required),
      raza:             new FormControl('',Validators.required),
      peso:             new FormControl('')
    });
    this.turnero = this.fb.group({
      fecha:               new FormControl('', [Validators.required,fechaDesdeHoyValidator]),
      hora:             new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.rol = usuario?.rol  
    });

    if(this.rol=="VETERINARIA"){
      this.getVeterinarios()
    } else{
      this.esVeterinarioLogueado = true;
    }

    this.getClientes();
    this.getEspecies();
  }

  getVeterinarios(){
    this.veterinarieService.getProfesionalesPorVeterinaria().subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.veterinarios=data.profesionales;
        } else{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: data.mensaje,
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
            cancelText: 'Cerrar',
          }
        });
      }
    })
  }

  getEspecies(){
    this.especieService.getAll().subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.especies = data.especies;
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: data.mensaje,
                cancelText: 'Cerrar'
              }
            });
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error interno al bucar las especies",
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getRazas(id: number){
    this.razasService.getAll(id).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.razas = data.razas;
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: data.mensaje,
                cancelText: 'Cerrar'
              }
            });
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "ocurrio un error interno al obtener las razas",
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }


  getClientes(){
    this.turnoService.getClientesDeVeterinarieCompleto().subscribe({
      next: (data)=>{
        if (data.estado!= "ERROR"){
          console.log(data)
          this.clientes = data.clientes;
          this.clientesFiltrados = this.clientes;
        } else{
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
            body: 'Ocurrio un error al buscar los clientes',
            cancelText: 'Cerrar'
          }
        });
      }
    });
  }
    
  filtrarClientesPorDni(){
    this.clienteSeleccionado = null;
    this.idClienteTurno=0;
    this.dniClienteTurno="";
    this.animalesDTO=[];
    this.animales=[];
    this.banderaNuevoAnimal=false;
    this.limpiarFormAnimal();
    this.esClienteNuevo=false;
    this.clientesFiltrados = this.clientes.filter(cliente =>
      cliente.dni.includes(this.inputDNI)
    );
  }

  displayClienteNombre(cliente: any): string {
    return cliente ? `${cliente.clienteNombre} ${cliente.clienteApellido}` : '';
  }
  
  onClienteSelected(cliente: ClienteDTO) {
    this.esClienteNuevo = false;
    this.banderaBuscarCliente=false;
    this.clienteSeleccionado = cliente;
    this.dniClienteTurno=cliente.dni;
    this.idClienteTurno=cliente.clienteId
    this.idAnimalTurno=0;
    //this.inputDNI="";
    this.banderaNuevoAnimal=false;
    this.idVeterinarioTurno=0;
    this.limpiarFormAnimal();
    //this.getAnimalesDeCliente(cliente.clienteId);
    this.getAnimalesByDni(cliente.dni);
  }

  getAnimalesDeCliente(id: number) {
    let a: AnimalDTO[] | undefined = [];
    a = this.clientes.find(cliente => cliente.clienteId === id)?.animales;
    

    if (a) {
        this.animalesDTO = [...a];
    }
  }

  buscarCliente(){
    //limpio los datos de cliente, animales y veterinario
    this.clienteSeleccionado = null;
    this.idClienteTurno=0; 
    this.dniClienteTurno="";
    this.animales=[];
    this.animalesDTO=[];
    this.nomApCliente = "";
    this.idAnimalTurno=0;
    this.limpiarFormAnimal();
    this.banderaBuscarCliente = true;
    this.idVeterinarioTurno = 0;
    this.esClienteNuevo=false;

    //busco el nuevo usuario
    this.usuarioService.getClinteByDni(this.inputDNI).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.clienteSeleccionado = data.usuario;
          //Si hay us en la bd entonces busco sus animales 
          if(this.clienteSeleccionado != null){
            this.idClienteTurno=data.usuario.id;
            this.dniClienteTurno=data.usuario.dni;
            this.getAnimalesByDni(this.clienteSeleccionado.dni);
            if(this.clienteSeleccionado.nombre && this.clienteSeleccionado.apellido){
              this.nomApCliente = this.clienteSeleccionado.nombre + " " + this.clienteSeleccionado.apellido;
            }
          } else if(!this.clienteSeleccionado){
            this.esClienteNuevo = true;
            this.dniClienteTurno = this.inputDNI;
            this.banderaNuevoAnimal = true;
            this.limpiarFormAnimal()
          }
        } else{
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
            body: 'Ocurrio un error al buscar los clientes',
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getAnimalesByDni(dni:string){
    this.animalService.getAnimales(dni).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.animales = data.animales
          if(this.animales.length ==0){
            this.banderaNuevoAnimal = true;
          }
        } else{
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
            body: 'Ocurrio un error al buscar los animales del cliente',
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  volver(){
    this.location.back();
  }
  nuevoAnimal(){
    this.idVeterinarioTurno = 0;
    this.idAnimalTurno=0
    this.banderaNuevoAnimal = true;
    this.limpiarFormAnimal();
  }

  selectAnimal(){
    this.limpiarFormAnimal();
    this.banderaNuevoAnimal = false;
    this.idVeterinarioTurno=0
  }

  limpiarFormAnimal(){
    this.mascota.reset({
      nombreMascota: '',
      foto: '',
      fechaNacMascota: '',
      especie: '',
      raza: '',
      peso: ''
    });
    this.limpiarFormTurnero();
  }

  limpiarFormTurnero(){
    this.busquedaTurnos=false;
    this.horarios=[];
    this.turnero.reset({
      fecha: '',
      hora: ''
    });
  }


  getHorariosDisponibles(){
    this.turnero.get('hora')?.setValue(null);
    let rq: DisponibilidadRq = new DisponibilidadRq();
    //rq.fecha = this.obtenerFechaFormateada();
    rq.fecha = this.turnero.get('fecha')?.value;
    rq.veterinarioId = this.idVeterinarioTurno;
    this.turnoService.disponibilidad(rq).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.horarios = data.horariosDisponibles;
            this.busquedaTurnos = true;
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


  onDialogNuevoTurno(){
    const fecha: Date = this.turnero.get('fecha')?.value;
    const dia = String(fecha.getDate()).padStart(2, '0');
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const anio = fecha.getFullYear();
    const fechaFormateada = `${dia}/${mes}/${anio}`;

    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Reservar turno',
        body: '¿Está seguro de que desea reservar un turno el día: ' + fechaFormateada + 'a las ' + this.turnero.get('hora')?.value.horaInicio + 'hs?' ,
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.reservarTurno();
        }
      }
    });
  }

  reservarTurno(){
    let rq: ReservarTurnoRq = this.getObject();
    console.log('RQ: ', rq);
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

  getObject(): ReservarTurnoRq {
      let req: ReservarTurnoRq = new ReservarTurnoRq();
      //esDomicilio se setea en el back segun la modalidad del profesional/veterinaria que esta haciendo la reserva
      req.fecha =  this.turnero.get('fecha')?.value;;
      req.hora = this.turnero.get('hora')?.value.horaInicio
      req.veterinarioId = this.idVeterinarioTurno //0 en caso de ser un veterinario el logueado
      
      if(this.banderaNuevoAnimal){
        //Armar Rq de nuevo animal
        let animal: AnimalRq = new AnimalRq();
        animal.nombre=this.mascota.get('nombreMascota')?.value;
        animal.razaId=this.mascota.get('raza')?.value.id;
        animal.fechaNac=this.mascota.get('fechaNacMascota')?.value;
        animal.peso=this.mascota.get('peso')?.value;
        //usuario id se resuelve en backend
        req.animalRq=animal
      } else{
        req.animalId=this.idAnimalTurno;
      }

      req.dniCliente=this.dniClienteTurno;
      
    
      return req;
    }

  habilitarDisponibilidad(){
    if(this.esClienteNuevo){
      return this.esClienteNuevo && this.mascota.valid
    } else if(this.clienteSeleccionado){
      return this.banderaNuevoAnimal? this.mascota.valid : this.idAnimalTurno!=0
    } else{
      console.log('no hay nuevo cliente ni cliente seleccionado ')
      return false;
    }
  }

}
