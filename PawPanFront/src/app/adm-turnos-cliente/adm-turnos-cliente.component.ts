import { CommonModule, Location } from '@angular/common';
import { Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { MatExpansionModule } from '@angular/material/expansion';
import { Turno } from '../model/Turno';
import { TurnoService } from '../services/Turno.service';
import { FiltroTurnoRq } from '../model/FiltroTurnoRq';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { TurnoFb } from '../model/TurnoFb';
import { AuthService } from '../services/auth.service';
import { estados } from '../model/data/data-Estados';
import { MatTable, MatTableModule } from '@angular/material/table';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
import { MatOption, MatSelect } from '@angular/material/select';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { VeterinariesService } from '../services/Veterinaries-service';
import { ProfesionalesPorVeterinaria } from '../model/ProfesionalPorVeterinaria';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Animal } from '../model/Animal';
import { AnimalService } from '../services/animal.service';
import { AtenderTurnoRq } from '../model/AtenderTurnoRq';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-adm-turnos-cliente',
  standalone: true,
  imports: [MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    MatExpansionModule,
    MatTable,
    MatDatepicker,
    MatSelect,
    MatOption,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatTableModule,
    MatTooltipModule,
    MatPaginatorModule
  ],
  templateUrl: './adm-turnos-cliente.component.html',
  styleUrl: './adm-turnos-cliente.component.scss'
})
export class AdmTurnosClienteComponent {
  authService = inject(AuthService); 
  private _snackBar = inject(MatSnackBar);
  cuilUs: string;

  filtros: FiltroTurnoRq;
  dataSource: TurnoFb[];
  total: number

  mascotas: Animal[];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  displayedColumns: string[] = 
  [
    'fecha',
    'animal',
    'estado',
    'veterinario',
    'veterinaria',
    'acciones'
  ];

  estados = estados;

  constructor(
    private router: Router,
    private turnoService: TurnoService,
    private dialog: MatDialog,
    private location: Location,
    private service: AnimalService,
  ){
    
  }

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.cuilUs = usuario?.cuil  
    });
    this.filtros = new FiltroTurnoRq();
    this.filtros.page=0;
    this.filtros.size=10;
    this.filtros.orderDir="DESC"
    this.filtros.orderBy="fecha_hora"
    this.getTurnos();
    this.getMascotas();
  }

  getMascotas(){
    this.service.getAnimales(this.cuilUs).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.mascotas=data.animales;
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

  getTurnos(){
    this.turnoService.getMisTurnos(this.filtros).subscribe({
      next: (data)=>{
        if(data.estado!="ERROR"){
          this.dataSource=data.turnos;
          this.total = data.total;
        }else{
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
      }, error:(error)=>{
        console.log(error);
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'Ocurrio un error al buscar los turnos',
            acceptText: 'Cerrar',
            onAccept: () => {
              this.location.back();
            }
          }
        });
      }
    });
  }

  getNombreEstado(idEstado: number): string|undefined {
    let estado = this.estados.find(e => e.id === idEstado)
    return estado?.descripcion
  }

  onDialogConfirmar(turno: TurnoFb, accion:string){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: accion +' turno',
        body: '¿Estás segura de que querés continuar?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.cancelarTurno(turno.id);
        }
      }
    });
  }
  
  cancelarTurno(idTurno: number){
      let rq: AtenderTurnoRq = new AtenderTurnoRq();
      rq.idTurno=idTurno;
      this.turnoService.cancelar(rq).subscribe({
        next: (data)=>{
          if (data.estado!= "ERROR"){
            this.openSnackBar('Turno cancelado!', 'Cerrar');
            this.getTurnos();
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
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: 'Ocurrio un error al guardar la atencion',
              cancelText: 'Cerrar'
            }
          });
        }
      });
    }

  paginado(event:any){
		this.filtros.page = event.pageIndex;
    this.getTurnos();
	}

  onVer(id:number){
    this.router.navigate(['ver-turno/'+id])
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

  volver(){
    this.location.back()
  }

  onNuevoTurno(){
    this.router.navigate(['adm-reservar-turno']);
  }
}
