import { Component, Input, OnInit } from '@angular/core';
import { ReportesServiceService } from '../../services/reportes-service.service';
import { ReporteRq } from '../../model/ReporteRq';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { CommonModule, JsonPipe, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { TurnosPorVeterinario } from '../../model/TurnosPorVeterinario';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../../model/dialog/generic-dialog/generic-dialog.component';
import { estados } from '../../model/data/data-Estados';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-turnos-por-veterinario',
  standalone: true,
  imports: [
    MatFormFieldModule, 
    MatDatepickerModule, 
    FormsModule, 
    ReactiveFormsModule, 
    JsonPipe,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatTableModule,
    MatCheckboxModule,
    CommonModule
  ],
  providers:[provideNativeDateAdapter()],
  templateUrl: './turnos-por-veterinario.component.html',
  styleUrls: ['./turnos-por-veterinario.component.scss']
})
export class TurnosPorVeterinarioComponent implements OnInit {
  estado: string = ""
  idEstado: number;

  estados = estados;
  estadosSeleccionados: number[] = [];

  displayedColumns: string[] = ['veterinario','cantidad', 'total','porcentaje']
  dataSource:TurnosPorVeterinario[];

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(
    private service: ReportesServiceService,
    private location: Location,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ){
    
  }

  ngOnInit(): void {
    this.estados=estados.filter(e => e.id !== null);
    this.route.paramMap.subscribe(params => {
      this.idEstado = Number(params.get('id'));
      this.getDatos();
    });
  }

  // getDatos(){
  //   switch(this.idEstado){
  //     case 5: //Turnos atendidos 
  //       this.estado=" ATENDIDOS ";
  //       this.getAtendidos();
  //       break;
  //     case 6://turnos pendientes de atencion por veterinario
  //       this.estado = " PENDIENTES DE ATENCION ";
  //       this.getPendientesAtencion();
  //       break;
  //     case 7: //turnos rechazados por veterinario
  //       this.estado = " RECHAZADOS ";
  //       this.getRechazados();
  //       break;
  //     default:
  //       this.volver();
  //       break;
  //   }
  // }

  getDatos(){
    let rq: ReporteRq = this.getRq();
    this.service.obtenerPorcentajePorVeterinario(rq).subscribe({
      next:(data)=>{
        this.dataSource=data;
      }, error:(error)=>{
        console.log(error);
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'Falló la busqueda del reporte',
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
      }
    });
  }

  // getAtendidos(){
  //   let rq: ReporteRq = this.getRq();
  //   this.service.getAtendidos(rq).subscribe({
  //     next:(data)=>{
  //       this.dataSource=data;
  //     }, error:(error)=>{
  //       console.log(error);
  //       this.dialog.open(GenericDialogComponent, {
  //         data: {
  //           type: 'error',
  //           title: '¡Algo salió mal!',
  //           body: 'Falló la busqueda del reporte',
  //           acceptText: 'Cerrar',
  //           onAccept: () => {
  //             this.location.back();
  //           }
  //         }
  //       });
  //     }
  //   });
  // }

  // getPendientesAtencion(){
  //   let rq: ReporteRq = this.getRq();
  //   this.service.getPendientes(rq).subscribe({
  //     next:(data)=>{
  //       this.dataSource=data;
  //     }, error:(error)=>{
  //       console.log(error);
  //       this.dialog.open(GenericDialogComponent, {
  //         data: {
  //           type: 'error',
  //           title: '¡Algo salió mal!',
  //           body: 'Falló la busqueda del reporte',
  //           acceptText: 'Cerrar',
  //           onAccept: () => {
  //             this.location.back();
  //           }
  //         }
  //       });
  //     }
  //   });
  // }

  // getRechazados(){
  //   let rq: ReporteRq = this.getRq();
  //   this.service.getRechazados(rq).subscribe({
  //     next:(data)=>{
  //       this.dataSource=data;
  //     }, error:(error)=>{
  //       console.log(error);
  //       this.dialog.open(GenericDialogComponent, {
  //         data: {
  //           type: 'error',
  //           title: '¡Algo salió mal!',
  //           body: 'Falló la busqueda del reporte',
  //           acceptText: 'Cerrar',
  //           onAccept: () => {
  //             this.location.back();
  //           }
  //         }
  //       });
  //     }
  //   });
  // }

  getRq(): ReporteRq{
    let rq: ReporteRq=new ReporteRq();
    rq.fechaInicio=this.range.get('start')?.value;
    rq.fechaFin=this.range.get('end')?.value;
    rq.idsEstados=this.estadosSeleccionados;
    return rq;
  }

  onEstadoChange(id: number, checked: boolean) {
    if (checked) {
      this.estadosSeleccionados.push(id);
    } else {
      this.estadosSeleccionados = this.estadosSeleccionados.filter(e => e !== id);
    }
  }

  volver(){
    this.location.back();
  }

}
