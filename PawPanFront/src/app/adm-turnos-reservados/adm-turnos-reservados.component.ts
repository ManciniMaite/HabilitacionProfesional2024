import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { misTurnosGetRq } from '../model/misTurnosGetRq';
import { TurnoService } from '../services/Turno.service';
import { MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-adm-turnos-reservados',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatDatepickerModule,
    ReactiveFormsModule
  ],
  templateUrl: './adm-turnos-reservados.component.html',
  styleUrl: './adm-turnos-reservados.component.scss'
})
export class AdmTurnosReservadosComponent {
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

  //turnos: Turno[] = Turnos;

  role: string;
  filtro: misTurnosGetRq = new misTurnosGetRq;

  // filters: { [key: string]: FormControl } = {};

  constructor(
    private turnoService: TurnoService,
    private authService: AuthService,
  ){}

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.role = usuario?.rol;
    });
    // this.dataSourcePaginada.turnos = this.data;

    // Crear FormControls para cada columna
    this.filtro.page = 0;
    this.filtro.size = 10;
    this.filtro.orderDir ="desc";
    this.filtro.orderBy = "fecha_hora";
    this.getTurnos();

    // this.displayedColumns.forEach(column => {
    //   this.filters[column] = new FormControl('');
    //   this.filters[column].valueChanges.subscribe(() => this.applyFilters());
    // });
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

  // applyFilters(): void {
  //   const filterValues = Object.entries(this.filters).reduce((acc, [key, control]) => {
  //     acc[key] = control.value ? control.value.toString().toLowerCase() : '';
  //     return acc;
  //   }, {} as { [key: string]: string });

  //   this.dataSourcePaginada = this.data.filter(item =>
  //     Object.entries(filterValues).every(([key, value]) =>
  //       !value || (item[key]?.toString().toLowerCase().includes(value))
  //     )
  //   );
  // }
}
