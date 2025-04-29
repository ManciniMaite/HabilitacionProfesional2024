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
import { UsuarioRequest } from '../model/UsuarioRq';
import { DomicilioRq } from '../model/DomicilioRq';
import { AnimalRq } from '../model/AnimalRq';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';

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

  date: Date = new Date();

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
    private ciudadesService: CiudadService,
    private dialog: MatDialog
  ){
    this.datosPersonales = this.fb.group({
      nombre:               new FormControl('', Validators.required),
      apellido:             new FormControl('', Validators.required),
      fechaNac:             new FormControl('', [Validators.required, validacionFecha(18)]),
      dni:                  new FormControl('', [Validators.required, validacionDni('dni')]),
      correo:               new FormControl('', [Validators.required, Validators.email,validacionFormatoCorreo]),
      telefono:             new FormControl('', [Validators.required, validacionTelefonoBasico]),
      contrasenia:          new FormControl('', [Validators.required, Validators.minLength(6)]),
      validarContrasenia:   new FormControl('', [Validators.required, Validators.minLength(6)]),
      preguntaSecreta:      new FormControl('', Validators.required),
      respuestaSecreta:     new FormControl('', Validators.required),
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
      this.ciudadesService.getAll().subscribe({
        next:(data)=> {
            if(data.estado != "ERROR"){
              this.ciudades = data.ciudades;
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
              body: "Ocurrio un error interno al obtener las ciudades",
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

  agregarAnimal(){
    let selectedAnimal = new Animal;
    selectedAnimal.nombre = this.mascota.value.nombreMascota;
    selectedAnimal.fechaNac = this.mascota.value.fechaNacMascota;
    selectedAnimal.raza = this.mascota.value.raza;
    selectedAnimal.peso = this.mascota.value.peso;
    this.animales.push(selectedAnimal);
  }

  quitarAnimal(item: Animal){
    let index
    if (this.animales.includes(item)){
      index = this.animales.indexOf(item);
      this.animales.splice(index, 1);
    }
  }
 
  onConfirmar(){
    if(this.validarDatosPersonales()){
      let rq : UsuarioRequest=this.getObject()
      console.log('us rq: ', rq)
      this.service.crearCuenta(rq).subscribe({
        next:(data) => {
          this.usCreado = true;
          console.log(data); 
        }, error: (error)=>{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: "Ocurrio un error al confirmar la creacion del usuario",
              cancelText: 'Cerrar'
            }
          });
          console.log(error);
        }
      });
    }
  }

  getObject():UsuarioRequest{
    let rq: UsuarioRequest = new UsuarioRequest();
    rq.tipoUsuario="PACIENTE";
    rq.telefono=this.datosPersonales.get('telefono')?.value;
    rq.correo=this.datosPersonales.get('correo')?.value;
    rq.contrasenia=this.datosPersonales.get('contrasenia')?.value;
    rq.nombre=this.datosPersonales.get('nombre')?.value;
    rq.apellido=this.datosPersonales.get('apellido')?.value;
    rq.dni=this.datosPersonales.get('dni')?.value;
    rq.fechaNac=this.datosPersonales.get('fechaNac')?.value;
    rq.pregunta=this.datosPersonales.get('preguntaSecreta')?.value;
    rq.respuesta=this.datosPersonales.get('respuestaSecreta')?.value;
    
    let animalesRq: AnimalRq[] = []
    console.log('animales: ',this.animales)
    this.animales.forEach(a =>{
      console.log(a);
      let animal: AnimalRq = new AnimalRq();
      animal.nombre = a.nombre;
      animal.fechaNac = a.fechaNac;
      animal.razaId=a.raza.id;
      animal.peso=a.peso;
      animalesRq.push(animal);
    })
    rq.animales = animalesRq;

    let domicilio: DomicilioRq = new DomicilioRq();
    domicilio.calle = this.ubicacion.get('calle')?.value;
    domicilio.ciudadId = this.ubicacion.get('ciudad')?.value;
    domicilio.numero= this.ubicacion.get('numero')?.value;
    domicilio.usuario=this.datosPersonales.get('dni')?.value;

    rq.domicilio = domicilio;
    
    
    return rq
    
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
           (this.datosPersonales.get('preguntaSecreta')?.value != null && this.datosPersonales.get('preguntaSecreta')?.value != undefined && this.datosPersonales.get('preguntaSecreta')?.value != "")&&
           (this.datosPersonales.get('respuestaSecreta')?.value != null && this.datosPersonales.get('respuestaSecreta')?.value != undefined && this.datosPersonales.get('respuestaSecreta')?.value != "")&&
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
