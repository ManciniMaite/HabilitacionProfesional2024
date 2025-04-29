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
import { AtenderTurnoRq } from '../model/AtenderTurnoRq';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-atender-turno',
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
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './atender-turno.component.html',
  styleUrl: './atender-turno.component.scss'
})
export class AtenderTurnoComponent implements OnInit {
  private _snackBar = inject(MatSnackBar);
  id: number;
  turno: Turno

  cargando: boolean = false;

  descripcion:string
  descripcionPrivada:string

  constructor(
    private service: TurnoService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private location: Location,
    private router: Router
  ){

  }

  ngOnInit(): void {
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

  onDialogSave(){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Registrar Atencion',
        body: '¿Estás seguro de que querés continuar?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.save();
        }
      }
    });
  }

  save(){
    let rq: AtenderTurnoRq = new AtenderTurnoRq();
    rq.idTurno=this.id;
    rq.descripcionPublica=this.descripcion;
    rq.descripcionPrivada=this.descripcionPrivada;
    this.service.atender(rq).subscribe({
      next: (data)=>{
        if (data.estado!= "ERROR"){
          this.openSnackBar('Cambios guardados!', 'Cerrar');
          this.router.navigate(['adm-turnos-veterinario']);
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
            body: 'Ocurrio un error al guardar la atencion',
            cancelText: 'Cerrar'
          }
        });
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
  volver(){
    this.location.back();
  }
}
