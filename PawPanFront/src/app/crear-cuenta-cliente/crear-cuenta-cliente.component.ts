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
import { MatChipsModule } from '@angular/material/chips';
import { Router, RouterLink } from '@angular/router';
import { UsuarioRq } from '../model/UsuarioRq';
import { Domicilio } from '../model/Domicilio';
import { UsuarioService } from '../services/usuario.service';
import { EspecieService } from '../services/especie.service';
import { RazaService } from '../services/raza.service';
import { CiudadService } from '../services/ciudad.service';
import { Ciudad } from '../model/Ciudad';
import { Especie } from '../model/Especie';
import { Raza } from '../model/Raza';
import { validacionFecha } from '../validators/validacionFecha';
import { validacionContraseniasIguales } from '../validators/validacionContraseniaIguales';
import { validacionFormatoCorreo } from '../validators/validarCorreo';
import { validacionTelefonoBasico } from '../validators/numeroTelefono';
import { validacionDni } from '../validators/validacionDni';

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
    MatChipsModule,
    RouterLink
  ],
  templateUrl: './crear-cuenta-cliente.component.html',
  styleUrl: './crear-cuenta-cliente.component.scss'
})
export class CrearCuentaClienteComponent implements OnInit{
  animationDuration = '1000'

  datosPersonales: FormGroup;
  mascota: FormGroup;
  ubicacion: FormGroup;

  animales: Animal[] = [];

  ciudades: Ciudad[] = [];
  especies: Especie[] = [];
  razas: Raza[] = [];

  usCreado: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private location: Location,
    private service: UsuarioService,
    private especieService: EspecieService,
    private razasService: RazaService,
    private ciudadesService: CiudadService
  ){
    this.datosPersonales = this.fb.group({
      nombre:               new FormControl('', Validators.required),
      apellido:             new FormControl('', Validators.required),
      fechaNac:             new FormControl('', [Validators.required, validacionFecha(18)]),
      dni:                  new FormControl('', [Validators.required, validacionDni('dni')]),
      correo:               new FormControl('', [Validators.required, Validators.email,validacionFormatoCorreo]),
      telefono:             new FormControl('', [Validators.required, validacionTelefonoBasico]),
      contrasenia:          new FormControl('', [Validators.required, Validators.minLength(6)]),
      validarContrasenia:   new FormControl('', [Validators.required, Validators.minLength(6)])
    }, { validators: validacionContraseniasIguales });

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

  ngOnInit(): void {
      this.especieService.getAll().subscribe({
        next:(data)=> {
            if(data.estado != "ERROR"){
              this.especies = data.especies;
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
      this.ciudadesService.getAll().subscribe({
        next:(data)=> {
            if(data.estado != "ERROR"){
              this.ciudades = data.ciudades;
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

  getRazas(id: number){
    this.razasService.getAll(id).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.razas = data.razas;
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
    if (this.validarDatosPersonales() && this.validarMascotas()){
      let rq: UsuarioRq = new UsuarioRq();
      rq.tipoUsuario="PACIENTE";
      rq.telefono=this.datosPersonales.get('telefono')?.value;
      rq.correo=this.datosPersonales.get('telefono')?.value;
      rq.contrasenia=this.datosPersonales.get('contrasenia')?.value;
      rq.nombre=this.datosPersonales.get('nombre')?.value;
      rq.apellido=this.datosPersonales.get('apellido')?.value;
      rq.dni=this.datosPersonales.get('dni')?.value;
      rq.fechaNac=this.datosPersonales.get('fechaNac')?.value;
      rq.animales = this.animales;

      let domicilio: Domicilio = new Domicilio();
      domicilio.calle = this.ubicacion.get('calle')?.value;
      domicilio.ciudad = this.ubicacion.get('ciudad')?.value;
      domicilio.numero= this.ubicacion.get('numero')?.value;

      rq.domicilio = domicilio;

      this.service.crearCuenta(rq).subscribe({
        next:(data) => {
          this.usCreado = true;
          console.log(data); 
        }, error: (error)=>{
          console.log(error);
        }
      });
      
    }
    console.log("Cuenta creada! datos:");
    console.log(this.datosPersonales.value);
    console.log(this.mascota.value);
    console.log(this.ubicacion.value);
  }

  volver(){
    this.location.back();
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

  validarMascotas(): boolean{
    return this.animales.length > 0;
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
