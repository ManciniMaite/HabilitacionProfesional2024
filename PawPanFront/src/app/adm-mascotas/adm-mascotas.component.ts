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
import { AnimalService } from '../services/animal.service';
import { AuthService } from '../services/auth.service';
import { Animal } from '../model/Animal';
import { AgregarAnimalComponent } from '../model/dialog/agregar-animal/agregar-animal.component';

@Component({
  selector: 'app-adm-mascotas',
  standalone: true,
  imports: [
    MatSnackBarModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatButton,
    MatTableModule
  ],
  templateUrl: './adm-mascotas.component.html',
  styleUrl: './adm-mascotas.component.scss'
})
export class AdmMascotasComponent {
  authService = inject(AuthService); 
  cuilUs: string;
  
  dataSource: Animal[] = [];

  displayedColumns: string[] = ['nombre','raza','peso', 'acciones']


  private _snackBar = inject(MatSnackBar);

  constructor(
    private service: AnimalService,

    private dialog: MatDialog
  ){}

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.cuilUs = usuario?.cuil  
    });
      this.getDatos();
  }

  getDatos(){
    this.service.getAnimales(this.cuilUs).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.dataSource = data.animales;
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

  onDialogQuitaranimal(animal:Animal){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Quitar Animal',
        body: '¿Está seguro que desea eliminar a '+ animal.nombre + '  de sus mascotas?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.eliminarAnimal(animal.id)
        }
      }
    });
  }

  eliminarAnimal(id:number){
    this.service.deleteAnimales(id).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.openSnackBar('La mascota ha sido eliminada', 'Cerrar');
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

  onDialogAgregarAnimal(){
    let dialog = this.dialog.open(AgregarAnimalComponent, {
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
