import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { TurnoService } from '../services/Turno.service';
import { misTurnosGetRq } from '../model/misTurnosGetRq';
import { misTurnosGetRs } from '../model/misTurnosGetRs';

@Component({
  selector: 'app-adm-mis-turnos',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatDatepickerModule,
    ReactiveFormsModule
  ],
  templateUrl: './adm-mis-turnos.component.html',
  styleUrl: './adm-mis-turnos.component.scss'
})
export class AdmMisTurnosComponent {

  displayedColumns: string[] = [
    'id', 'raza', 'especie', 'fecha', 'idAnimal', 'idVeterinario',
    'idVeterinaria', 'nombreAnimal', 'nombreVeterinario', 'nombreVeterinaria'
  ];

  dataSourcePaginada : {
    id : number;
    raza : string;
    especie : string;
    fecha : Date;
    idAnimal : number;
    idVeterinario :number;
    idVeterinaria : number;
    nombreAnimal : string;
    nombreVeterinario : string;
    nombreVeterinaria : string;
}[]; 
  // = new MatTableDataSource<any>([]);

  data: any[] = [
    {
      "id": 6,
      "raza": "Sin especificar",
      "especie": "perro",
      "fecha": "2025-04-29T06:00:00",
      "idAnimal": 1,
      "idVeterinario": 11,
      "idVeterinaria": null,
      "nombreAnimal": "Nailon",
      "nombreVeterinario": "Marcela Rivero",
      "nombreVeterinaria": null
    },
  ];

  filtro: misTurnosGetRq;

  filters: { [key: string]: FormControl } = {};

  constructor(
    private turnoService: TurnoService
  ){}

  ngOnInit(): void {
    // this.dataSourcePaginada.turnos = this.data;

    // Crear FormControls para cada columna
    this.displayedColumns.forEach(column => {
      this.filters[column] = new FormControl('');
      this.filters[column].valueChanges.subscribe(() => this.applyFilters());
    });
  }

  getTurnos(){
    this.turnoService.getMisTurnos(this.filtro).subscribe({
      next:(data) => {
        if(data && data.estado == "OK"){
          this.dataSourcePaginada = data.turnos;
        }
      }
    })
  }

  applyFilters(): void {
    const filterValues = Object.entries(this.filters).reduce((acc, [key, control]) => {
      acc[key] = control.value ? control.value.toString().toLowerCase() : '';
      return acc;
    }, {} as { [key: string]: string });

    this.dataSourcePaginada = this.data.filter(item =>
      Object.entries(filterValues).every(([key, value]) =>
        !value || (item[key]?.toString().toLowerCase().includes(value))
      )
    );
  }



}
