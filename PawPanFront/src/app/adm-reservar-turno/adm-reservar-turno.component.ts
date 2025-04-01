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
import { AppComponent } from '../app.component';
import { Usuario } from '../model/Usuario';

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
  animationDuration = '1000';

  usuari: Usuario

  mascota: FormGroup;
  domicilio: FormGroup;
  veterinaries: FormGroup;
  turnero: FormGroup;

  domUsuario: Domicilio[];

  mascotas: Animal[] = [];

  veterinarias: Veterinaria[];

  empleadosVete: Veterinario[];

  veterinarios: Veterinario[];

  veterinariosLista: {nombre: string, dniCuil: string, tipo: string}[] = [];

  veterinariaSelected:{nombre: string, dniCuil: string, tipo: string};

  horarios: Horario[] = [];

  usCreado: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private location: Location,
    private animalService: AnimalService,
    private veterinariesService: VeterinariesService,
    private domicilioService: DomicilioService,
    private turnoService: TurnoService,
  ){

    this.mascota = this.fb.group({
      nombreMascota:    new FormControl('', Validators.required) //checkbox de mascotas
    });

    this.domicilio = this.fb.group({
      aDomicilio:    new FormControl('', Validators.required), //radio button para atencion a domicilio o no
      domicilioUsuario:     new FormControl('') //domicilios que el usuario selecciona
    })

    this.veterinaries = this.fb.group({
      vetes:               new FormControl('', Validators.required),
      empleadoVete:             new FormControl('')
    });

    this.turnero = this.fb.group({
      fecha:               new FormControl('', Validators.required),
      hora:             new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      console.log('getUS, ', usuario)
      this.getAnimales();
      this.getDomicilios(usuario.cuil);
      // this.getAnimales(usuario.cuil);
      // this.getDomicilios(usuario.cuil);
    });
  }

  veteMetod(vete:{nombre: string, dniCuil: string, tipo: string}){
    this.empleadosVete = [];
    this.veterinariaSelected = vete;
    if (vete.tipo == 'veterinaria'){
      for (const veterinaria of this.veterinarias) {
        if (veterinaria.cuit == vete.dniCuil) {
          this.empleadosVete = veterinaria.veterinarios;
        };
      };
    }
  };

  getAnimales(){
    this.animalService.getAnimales().subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.mascotas = data.animales;
          } else {
            /**
             * TODO: DIALOGO DE ERROR
             */
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        /**
         * TODO: DIALOGO DE ERROR 
         */
        console.log(error);
      }
    });
  }

  getDomicilios(cuil: string){
    this.domUsuario = [];
    this.domicilioService.getDoms(cuil).subscribe({
      next:(data)=> {
        this.domUsuario=data;
          // if(data.estado != "ERROR"){
          //   this.domUsuario = data.domicilios;
          // } else {
          //   /**
          //    * TODO: DIALOGO DE ERROR
          //    */
          //   console.log(data.mensaje);
          // }
      }, error: (error)=>{
        /**
         * TODO: DIALOGO DE ERROR 
         */
        console.log(error);
      }
    });
  }

  getVeterinaries(idCiudad: number){
    this.veterinariosLista = [];
    this.veterinariesService.getAll(idCiudad).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.veterinarias = data.veterinarias;
            for (const vetes of this.veterinarias) {
              this.veterinariosLista.push({
                nombre: vetes.razonSocial,
                dniCuil: vetes.cuit,
                tipo: 'veterinaria'
              });
            }

            this.veterinarios = data.veterinariosIndependientes;
            for (const vetes of this.veterinarios) {
              this.veterinariosLista.push({
                nombre: vetes.nombre + ' ' + vetes.apellido,
                dniCuil: vetes.dni,
                tipo: 'veterinario'
              });
            }
            
          } else {
            /**
             * TODO: DIALOGO DE ERROR
             */
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        /**
         * TODO: DIALOGO DE ERROR 
         */
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
      return `${dia}/${mes}/${anio}`;
    }
    return '';
  }

  getHorariosDisponibles(){
    let rq: DisponibilidadRq = new DisponibilidadRq();
    rq.fecha = this.obtenerFechaFormateada();
    rq.dniCuil = parseInt(this.veterinariaSelected.dniCuil);
    console.log("Require de disponibilidad: ",rq);
    this.turnoService.disponibilidad(rq).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.horarios = data.horarios;
            console.log('Horarios: ',this.horarios)
          } else {
            /**
             * TODO: DIALOGO DE ERROR
             */
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        /**
         * TODO: DIALOGO DE ERROR 
         */
        console.log(error);
      }
    });
  }

  // agregarAnimal(){
  //   let selectedAnimal = new Animal;
  //   selectedAnimal.nombre = this.mascota.value.nombreMascota;
  //   selectedAnimal.fechaNac = this.mascota.value.fechaNacMascota;
  //   selectedAnimal.raza.especie = this.mascota.value.especie;
  //   selectedAnimal.raza = this.mascota.value.raza;
  //   selectedAnimal.peso = this.mascota.value.peso;
  //   this.animales.push(selectedAnimal);
  //   console.log("animal seleccionado: ", selectedAnimal);
  //   console.log("array de animales: ", this.animales);
  // }

  // quitarAnimal(item: Animal){
  //   let index
  //   if (this.animales.includes(item)){
  //     index = this.animales.indexOf(item);
  //     this.animales.splice(index, 1);
  //   }
  // }
 
  // onConfirmar(){
  //   if (this.validarDatosPersonales() && this.validarMascotas()){
  //     let rq: UsuarioRq = new UsuarioRq();
  //     rq.tipoUsuario="PACIENTE";
  //     rq.telefono=this.datosPersonales.get('telefono')?.value;
  //     rq.correo=this.datosPersonales.get('telefono')?.value;
  //     rq.contrasenia=this.datosPersonales.get('contrasenia')?.value;
  //     rq.nombre=this.datosPersonales.get('nombre')?.value;
  //     rq.apellido=this.datosPersonales.get('apellido')?.value;
  //     rq.dni=this.datosPersonales.get('dni')?.value;
  //     rq.fechaNac=this.datosPersonales.get('fechaNac')?.value;
  //     rq.animales = this.animales;

  //     let domicilio: Domicilio = new Domicilio();
  //     domicilio.calle = this.ubicacion.get('calle')?.value;
  //     domicilio.ciudad = this.ubicacion.get('ciudad')?.value;
  //     domicilio.numero= this.ubicacion.get('numero')?.value;

  //     rq.domicilio = domicilio;

  //     // this.service.crearCuenta(rq).subscribe({
  //     //   next:(data) => {
  //     //     this.usCreado = true;
  //     //     console.log(data); 
  //     //   }, error: (error)=>{
  //     //     console.log(error);
  //     //   }
  //     // });
      
  //   }
  //   console.log("Cuenta creada! datos:");
  //   console.log(this.datosPersonales.value);
  //   console.log(this.mascota.value);
  //   console.log(this.ubicacion.value);
  // }

  volver(){
    this.location.back();
  }

  // validarDatosPersonales(): boolean{
  //   return (this.datosPersonales.get('nombre')?.value != null && this.datosPersonales.get('nombre')?.value != undefined && this.datosPersonales.get('nombre')?.value != "")&&
  //          (this.datosPersonales.get('apellido')?.value != null && this.datosPersonales.get('apellido')?.value != undefined && this.datosPersonales.get('apellido')?.value != "")&&
  //          (this.datosPersonales.get('fechaNac')?.value != null && this.datosPersonales.get('fechaNac')?.value != undefined && this.datosPersonales.get('fechaNac')?.value != "")&&
  //          (this.datosPersonales.get('dni')?.value != null && this.datosPersonales.get('dni')?.value != undefined && this.datosPersonales.get('dni')?.value != "")&&
  //          (this.datosPersonales.get('correo')?.value != null && this.datosPersonales.get('correo')?.value != undefined && this.datosPersonales.get('correo')?.value != "")&&
  //          (this.datosPersonales.get('telefono')?.value != null && this.datosPersonales.get('telefono')?.value != undefined && this.datosPersonales.get('telefono')?.value != "")&&
  //          (this.datosPersonales.get('contrasenia')?.value != null && this.datosPersonales.get('contrasenia')?.value != undefined && this.datosPersonales.get('contrasenia')?.value != "")&&
  //          (this.datosPersonales.get('validarContrasenia')?.value != null && this.datosPersonales.get('validarContrasenia')?.value != undefined && this.datosPersonales.get('validarContrasenia')?.value != "")&&
  //          this.validarContrasenias();
  // }

  // validarContrasenias(): boolean{
  //   return this.datosPersonales.get('contrasenia')?.value === this.datosPersonales.get('validarContrasenia')?.value;
  // }

  // validarMascotas(): boolean{
  //   return this.animales.length > 0;
  // }

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
