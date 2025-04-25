import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import { Router, RouterLink } from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { AuthService } from '../services/auth.service';
import { SessionManagerRequest } from '../model/SessionManagerRequest';
import { HttpClientModule } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [ MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    HttpClientModule
  ],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss'
})
export class InicioComponent implements OnInit{
  formGroup: FormGroup
  ngOnInit(): void {
      
  }

  constructor(
    private fb: FormBuilder,
    private routes: Router,
    private authService: AuthService,
    private dialog: MatDialog
  ){
    this.formGroup = this.fb.group({
      usuario:      new FormControl("",[Validators.required]),
      contrasenia:  new FormControl("", [Validators.required])
    });
  }
  
  ingresar(){
    let rq: SessionManagerRequest = new SessionManagerRequest();
    rq.correo = this.formGroup.get('usuario')?.value; 
    rq.password = this.formGroup.get('contrasenia')?.value
    this.authService.onLogIn(rq).subscribe({
      next:(data) => {
        if(data.estado=="OK"){
          this.authService.setUsuario(data.token, data.nombre, data.rol ,data.cuil);
          this.routes.navigate(['home']);
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
}
