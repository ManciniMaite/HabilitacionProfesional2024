import { CommonModule, Location } from '@angular/common';
import { Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
//import { Turnos } from '../model/data/data-Turnos';
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
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { AtenderTurnoRq } from '../model/AtenderTurnoRq';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClienteDTO } from '../model/ClienteDTO';
import { AnimalDTO } from '../model/AnimalDTO';
import { MatAutocomplete, MatAutocompleteModule } from '@angular/material/autocomplete';

@Component({
  selector: 'app-adm-turnos-reservados',
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
    MatPaginatorModule,
    MatAutocompleteModule
  ],
  templateUrl: './adm-turnos-veterinario.component.html',
  styleUrl: './adm-turnos-veterinario.component.scss'
})
export class AdmTurnosVeterinarioComponent implements OnInit {
  private _snackBar = inject(MatSnackBar);
  filtros: FiltroTurnoRq;
  dataSource: TurnoFb[];
  total: number;

  clientes: ClienteDTO[];
  clientesFiltrados: ClienteDTO[];
  animales: AnimalDTO[];
  inputDNI: string;
  clienteSeleccionado: ClienteDTO | null;

  displayedColumns: string[] = 
  [
    'fecha',
    'animal',
    'raza',
    'especie',
    'estado',
    'acciones'
  ];

  estados = estados;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(
    private router: Router,
    private turnoService: TurnoService,
    private dialog: MatDialog,
    private location: Location
  ){
    
  }

  ngOnInit(): void {
    this.filtros = new FiltroTurnoRq();
    this.filtros.page=0;
    this.filtros.size=10;
    this.filtros.orderDir="DESC"
    this.filtros.orderBy="fecha_hora"
    this.filtros.fecha = new Date().toISOString().split('T')[0];
    this.getTurnos();
    this.getClientes();
  }
  volver(){
    this.location.back();
  }

  getTurnos(){
    this.turnoService.getMisTurnos(this.filtros).subscribe({
      next: (data)=>{
        if(data.estado!="ERROR"){
          this.dataSource=[...data.turnos];
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
          switch(accion) { 
            case "Aceptar": { 
              this.aceptarTurno(turno.id);
              break; 
            } 
            case "Rechazar": { 
              this.rechazarTruno(turno.id);
              break; 
            } 
            case "Cancelar": { 
              this.cancelarTurno(turno.id); 
              break; 
            } 
            case "Atender": { 
              this.atenderTurno(turno.id);
              break; 
            } 
            default: { 
               //statements; 
               break; 
            } 
         } 
      
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

  aceptarTurno(idTurno: number){
    let rq: AtenderTurnoRq = new AtenderTurnoRq();
    rq.idTurno=idTurno;
    this.turnoService.aceptar(rq).subscribe({
      next: (data)=>{
        if (data.estado!= "ERROR"){
          this.openSnackBar('Turno aceptado!', 'Cerrar');
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

  atenderTurno(idTurno: number){
    this.router.navigate(['atender-turno/'+idTurno]);
  }

  rechazarTruno(idTurno: number){
    let rq: AtenderTurnoRq = new AtenderTurnoRq();
    rq.idTurno=idTurno;
    this.turnoService.rechazar(rq).subscribe({
      next: (data)=>{
        if (data.estado!= "ERROR"){
          this.openSnackBar('Turno rechazado!', 'Cerrar');
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

  getClientes(){
    this.turnoService.getClientesDeVeterinarie().subscribe({
      next: (data)=>{
        if (data.estado!= "ERROR"){
          this.clientes = data.clientes;
          this.clientesFiltrados = this.clientes;
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
            body: 'Ocurrio un error al buscar los clientes',
            cancelText: 'Cerrar'
          }
        });
      }
    });
  }

  displayClienteNombre(cliente: any): string {
    return cliente ? `${cliente.clienteNombre} ${cliente.clienteApellido}` : '';
  }
  
  onClienteSelected(cliente: any) {
    this.clienteSeleccionado = cliente;
    this.filtros.idCliente = cliente.clienteId; 
    this.getAnimalesDeCliente(cliente.clienteId);
  }

  filtrarClientesPorDni(){
    this.clientesFiltrados = this.clientes.filter(cliente =>
      cliente.dni.includes(this.inputDNI)
    );
  }

  limpiarFiltroCliente(){
    this.filtros.idCliente=0;
    this.filtros.idAnimal=0;
    this.inputDNI = '';
    this.clienteSeleccionado = null;
    this.clientesFiltrados = this.clientes;
  }

  getAnimalesDeCliente(id: number) {
    this.filtros.idAnimal=0;
    let a: AnimalDTO[] | undefined = [];
    
    if (this.filtros.idCliente != 0) {
        a = this.clientes.find(cliente => cliente.clienteId === id)?.animales;
    }

    if (a) {
        this.animales = [...a];
    }
  }


  paginado(event:any){
		this.filtros.page = event.pageIndex;
    this.getTurnos();
	}

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

  onVer(id:number){
    this.router.navigate(['ver-turno/'+id])
  }
  
  onNuevoTurno(){
    this.router.navigate(['nuevo-turno']);
  }
}
