import { Component, inject, OnInit } from '@angular/core';
import { VeterinariesService } from '../services/Veterinaries-service';
import { Veterinario } from '../model/Veterinario';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { AgregarVeterinarioComponent } from '../model/dialog/agregar-veterinario/agregar-veterinario.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';


@Component({
  selector: 'app-adm-veterinarios',
  standalone: true,
  imports: [
    MatSnackBarModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatButton,
    MatTableModule
  ],
  templateUrl: './adm-veterinarios.component.html',
  styleUrl: './adm-veterinarios.component.scss'
})
export class AdmVeterinariosComponent implements OnInit{

  dataSource = [];

  displayedColumns: string[] = ['nombre','apellido','matricula', 'acciones']


  private _snackBar = inject(MatSnackBar);

  constructor(
    private service: VeterinariesService,
    private dialog: MatDialog
  ){}

  ngOnInit(): void {
      this.getDatos();
  }

  getDatos(){
    this.service.getProfesionalesPorVeterinaria().subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.dataSource=data.profesionales;
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

  onDialogQuitarProfesional(v: Veterinario){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Quitar Profesional',
        body: '¿Está seguro que desea quitar a '+ v.nombre + ' ' + v.apellido + ' de sus profesionales?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.quitarProfesional(v);
        }
      }
    });
  }

  quitarProfesional(veterinario: Veterinario){
    this.service.quitarProfesional(veterinario.id).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.openSnackBar('Profesional quitado', 'Cerrar');
          this.getDatos();
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

  onDialogAgregarProfesional(){
    let dialog = this.dialog.open(AgregarVeterinarioComponent, {
      width: '700px'
    });

    dialog.afterClosed().subscribe(data => {
      if(data){
        this.getDatos();
      }
    })

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}
