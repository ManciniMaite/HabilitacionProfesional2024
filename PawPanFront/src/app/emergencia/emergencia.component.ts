import { Component } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { Emergencia } from '../model/Emergencia';
import { VeterinariesService } from '../services/Veterinaries-service';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { Location } from '@angular/common';

@Component({
  selector: 'app-emergencia',
  standalone: true,
  imports: [
    MatExpansionModule,
    MatIconModule
  ],
  templateUrl: './emergencia.component.html',
  styleUrl: './emergencia.component.scss'
})
export class EmergenciaComponent {

  contactos: Emergencia[];
  // contactos: Emergencia[] = [
  //   {
  //     nombre: "Juan Jose",
  //     domicilio: "Remedios de gatitos 340",
  //     telefono: "3535648789"
  //   },
  //   {
  //     nombre: "La vete del pueblo SA",
  //     domicilio: "en el centro",
  //     telefono: "3534278651"
  //   }
  // ];

  constructor(
    private veterinarieService: VeterinariesService,
    private dialog: MatDialog,
    private location: Location
  ){}

  ngOnInit(){
    this.getContactos();
  }

  getContactos(){
    this.veterinarieService.getEmergencia().subscribe({
      next:(data) =>{
        if (data && data.estado != "ERROR"){
          this.contactos = data.contactos;
        } else {
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: data.mensaje,
              acceptText: 'Cerrar',
              onAccept: () => {
                this.location.back();
              }
            }
          });
        }
      }, error: (error)=>{
        console.log(error);
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'Ocurrio un error al buscar los veterinarios o veterinarias disponibles',
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
      }
    });
  }

}
