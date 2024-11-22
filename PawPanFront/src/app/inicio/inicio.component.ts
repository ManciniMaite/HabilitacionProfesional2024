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
    private authService: AuthService
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
        console.log(data); 
        if(data.estado!="ERROR"){
          this.authService.setUsuario(data.token, data.nombre, data.rol);
          this.routes.navigate(['home']);
        }
      }, error: (error)=>{
        console.log(error);
      }
    });
  }
}
