import { JsonPipe, Location, TitleCasePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TurnosPorEspecie } from '../../model/TurnosPorEspecie';
import { ReporteRq } from '../../model/ReporteRq';
import { ReportesServiceService } from '../../services/reportes-service.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../../model/dialog/generic-dialog/generic-dialog.component';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-turnos-por-especie',
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
    TitleCasePipe
  ],
  providers:[provideNativeDateAdapter()],
  templateUrl: './turnos-por-especie.component.html',
  styleUrl: './turnos-por-especie.component.scss'
})
export class TurnosPorEspecieComponent {

  displayedColumns: string[] = ['especie','cantidad']
  dataSource:TurnosPorEspecie[]=[];

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(
    private service: ReportesServiceService,
    private location: Location,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ){}

  getDatos(){
    let rq: ReporteRq = this.getRq();
    this.service.getTurnosPorEspecie(rq).subscribe({
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

  getRq(): ReporteRq{
    let rq: ReporteRq=new ReporteRq();
    rq.fechaInicio=this.range.get('start')?.value;
    rq.fechaFin=this.range.get('end')?.value;
    return rq;
  }

  volver(){
    this.location.back();
  }

  getTotalTurnos(): number {
    let total: number = 0
    if(this.dataSource.length>0){
      this.dataSource.forEach(item => total+=item.cantidadTurnos)
    }
    return total
  }
}
