import { Component } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { Emergencia } from '../model/Emergencia';
import { VeterinariesService } from '../services/Veterinaries-service';

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

  contactos: Emergencia[] = [
    {
      nombre: "Juan Jose",
      domicilio: "Remedios de gatitos 340",
      telefono: "3535648789"
    },
    {
      nombre: "La vete del pueblo SA",
      domicilio: "en el centro",
      telefono: "3534278651"
    }
  ];

  constructor(
    private veterinarieService: VeterinariesService
  ){}

  ngOnInit(){
    this.getContactos();
  }

  getContactos(){
    this.veterinarieService.getEmergencia().subscribe({
      next:(data) =>{
        if (data && data.estado != "ERROR"){
          this.contactos = data.contactos;
        }
      }, error: (error)=>{
        console.log(error);
      }
    });
  }

}
