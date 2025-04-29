import { Component } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { Emergencia } from '../model/Emergencia';
import { VeterinariesService } from '../services/Veterinaries-service';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { Location } from '@angular/common';
import { MatLabel, MatSelectModule } from '@angular/material/select';
import { AuthService } from '../services/auth.service';
import { DomicilioService } from '../services/Domicilio.service';
import { Domicilio } from '../model/Domicilio';

@Component({
  selector: 'app-emergencia',
  standalone: true,
  imports: [
    MatExpansionModule,
    MatIconModule,
    MatSelectModule,
    MatLabel
  ],
  templateUrl: './emergencia.component.html',
  styleUrl: './emergencia.component.scss'
})
export class EmergenciaComponent {
  cuilUs: string;
  ciudades: Domicilio[];
  contactos: Emergencia[];
  mostrarContactos: boolean = false;
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
    private location: Location,
    private authService: AuthService,
    private domicilioService: DomicilioService,
  ){}

  ngOnInit(){
    this.authService.usuario$.subscribe(usuario => {
      this.cuilUs = usuario?.cuil;
      this.getCiudades();
    });
  }

  getCiudades(){
    this.domicilioService.getDoms(this.cuilUs).subscribe({
      next:(data)=>{
        this.ciudades = data;
      }, error:(error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: 'Quitar Animal',
            body: 'Ocurrio un error al obtener sus domicilios',
            cancelText: 'Cerrar',
          }
        });
      }
    });
  }

  onCiudadSelected(ciudad: number) {
    this.getContactos(ciudad);
  }

  getContactos(ciudad: number){
    this.veterinarieService.getEmergencia(ciudad).subscribe({
      next:(data) =>{
        if (data && data.estado != "ERROR"){
          this.contactos = data.contactos;
          this.mostrarContactos = true;
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
