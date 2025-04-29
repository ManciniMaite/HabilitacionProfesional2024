import { Component, inject, OnInit } from '@angular/core';
import { TurnoService } from '../services/Turno.service';
import { Turno } from '../model/Turno';
import { MatCard, MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { CommonModule, Location } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { TextFieldModule } from '@angular/cdk/text-field';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-ver-turno',
  standalone: true,
  imports: [
      MatCardModule,
      CommonModule,
      MatExpansionModule,
      MatCard,
      MatInputModule, 
      TextFieldModule,
      MatFormFieldModule,
      FormsModule,
      MatButton,
      MatIconModule,
      MatButtonModule
    ],
  templateUrl: './ver-turno.component.html',
  styleUrl: './ver-turno.component.scss'
})
export class VerTurnoComponent implements OnInit{
  authService = inject(AuthService); 
  rol: string
  id: number;
  turno: Turno

  cargando: boolean = false;

  descripcion:string

  constructor(
    private service: TurnoService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private location: Location,
    private router: Router
  ){

  }
  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.rol = usuario?.rol  
    });
    this.cargando=true;
    this.route.paramMap.subscribe(params => {
      this.id = Number(params.get('id'));
      this.service.getById(this.id).subscribe({
        next:(data)=>{
          this.cargando=false
          this.turno=data;
        }, error:(error)=>{
          this.cargando = false;
          console.log(error);
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: 'Falló la busqueda del turno',
              acceptText: 'Cerrar',
              onAccept: () => {
                this.location.back();
              }
            }
          });
        }
      });
    });
  }
  volver(){
    this.location.back();
  }
}
