import { CommonModule, Location } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { SessionManagerRequest } from '../model/SessionManagerRequest';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { UsuarioRequest } from '../model/UsuarioRq';
import { UsuarioService } from '../services/usuario.service';
import { validacionContraseniasIguales } from '../validators/validacionContraseniaIguales';

@Component({
  selector: 'app-recuperar-password',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    HttpClientModule
  ],
  templateUrl: './recuperar-password.component.html',
  styleUrl: './recuperar-password.component.scss'
})
export class RecuperarPasswordComponent {
  step: number = 1;
  preguntaSecreta: string;
  
  formGroup: FormGroup
  formGroupRespuesta: FormGroup
  formGroupNewPassword: FormGroup

  constructor(
    private fb: FormBuilder,
    private routes: Router,
    private location: Location,
    private usuarioService: UsuarioService,
    private dialog: MatDialog
  ){
    this.formGroup = this.fb.group({
      dniCuit:  new FormControl("",[Validators.required]),
      correo:   new FormControl("", [Validators.required, Validators.email])
    });
    this.formGroupRespuesta = this.fb.group({
      respuesta:  new FormControl("",[Validators.required])
    });
    this.formGroupNewPassword = this.fb.group({
      contrasenia:         new FormControl("", [Validators.required, Validators.minLength(6)]),
      validarContrasenia:  new FormControl("", [Validators.required, Validators.minLength(6)])
    }, { validators: validacionContraseniasIguales });
  }

  obtenerPregunta(){
    let rq: UsuarioRequest = new UsuarioRequest();
    rq.correo = this.formGroup.get('correo')?.value; 
    rq.dniCuit = this.formGroup.get('dniCuit')?.value;
    this.usuarioService.obtenerPregunta(rq).subscribe({
      next:(data) => {
        if(data.estado=="OK"){
          this.preguntaSecreta = data.pregunta;
          this.step = 2;
          // this.routes.navigate(['home']);
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
            cancelText: 'Cerrar',
          }
        });
      }
    });
  }

  respuestaSecreta(){
    let rq: UsuarioRequest = new UsuarioRequest();
    rq.correo = this.formGroup.get('correo')?.value;
    rq.respuesta = this.formGroupRespuesta.get('respuesta')?.value;
    this.usuarioService.respuestaSecreta(rq).subscribe({
      next:(data) => {
        if(data.estado=="OK"){
          this.step = 3;
          // this.routes.navigate(['home']);
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
            cancelText: 'Cerrar',
          }
        });
      }
    });
  }

  onConfirmar(){
    let rq: UsuarioRequest = new UsuarioRequest();
    rq.correo = this.formGroup.get('correo')?.value;
    rq.contrasenia = this.formGroupNewPassword.get('contrasenia')?.value;
    this.usuarioService.nuevaPassword(rq).subscribe({
      next:(data) => {
        if(data.estado=="OK"){
          this.dialog.open(GenericDialogComponent, {
            data: {
              title: '¡Cuenta creada con éxito!',
              body: "Ahora debes iniciar sesión con tu nueva contraseña",
              acceptText: "Iniciar Sesión",
              onAccept: () => {
                this.routes.navigate(['iniciar-sesion']);
              }
            }
          });
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
            cancelText: 'Cerrar',
          }
        });
      }
    });
  }

  validarContrasenias(): boolean{
    let passwordValida: boolean = (
      this.formGroupNewPassword.get('contrasenia')?.value != null &&
      this.formGroupNewPassword.get('contrasenia')?.value != undefined &&
      this.formGroupNewPassword.get('contrasenia')?.value != ""
    ) && (
      this.formGroupNewPassword.get('validarContrasenia')?.value != null &&
      this.formGroupNewPassword.get('validarContrasenia')?.value != undefined &&
      this.formGroupNewPassword.get('validarContrasenia')?.value != ""
    ) &&
    this.formGroupNewPassword.get('contrasenia')?.value === this.formGroupNewPassword.get('validarContrasenia')?.value;
    return passwordValida;
  }

  volver(){
    this.location.back();
  }

}
