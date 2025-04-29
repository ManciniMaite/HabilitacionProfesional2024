import { Component, inject, OnInit } from '@angular/core';
import { ItemReporte, itemsReportes } from '../model/data/data-ItemsReporte';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { CommonModule, Location } from '@angular/common';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TurnosPorVeterinarioComponent } from './turnos-por-veterinario/turnos-por-veterinario.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    CommonModule,
    MatTooltipModule,
    TurnosPorVeterinarioComponent
  ],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.scss'
})
export class ReportesComponent implements OnInit{

  authService = inject(AuthService);
  role:string; 

  displayedColumns: string[] = ['nombre', 'acciones']
  dataSource: ItemReporte[] = itemsReportes;

  constructor(
    private location: Location,
    private router: Router,
  ){

  }

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.role = usuario?.rol;
    });
    this.dataSource = itemsReportes.filter(item => item.rol.includes(this.role));
  }

  generarReporte(path: string){
    console.log('path: ', path)
    this.router.navigate([path]);
  }
  
  volver(){
    this.location.back();
  }
}
