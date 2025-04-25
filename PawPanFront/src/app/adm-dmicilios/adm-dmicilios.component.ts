import { Component, inject } from '@angular/core';
import { Domicilio } from '../model/Domicilio';
import { MatDialog } from '@angular/material/dialog';
import { GenericDialogComponent } from '../model/dialog/generic-dialog/generic-dialog.component';
import { AuthService } from '../services/auth.service';
import { DomicilioService } from '../services/Domicilio.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { NuevoDomicilioComponent } from '../model/dialog/nuevo-domicilio/nuevo-domicilio.component';
import { DomicilioRq } from '../model/DomicilioRq';

@Component({
  selector: 'app-adm-dmicilios',
  standalone: true,
  imports: [
      MatSnackBarModule,
      MatCardModule,
      MatButtonModule,
      MatIconModule,
      MatButton,
      MatTableModule
    ],
  templateUrl: './adm-dmicilios.component.html',
  styleUrl: './adm-dmicilios.component.scss'
})
export class AdmDmiciliosComponent {
  private _snackBar = inject(MatSnackBar);
  authService = inject(AuthService); 
  cuilUs: string;
    
  dataSource: Domicilio[]

  displayedColumns: string[] = ['ciudad','calle','numero', 'acciones']

  constructor(
    private dialog: MatDialog,
    private service: DomicilioService
  ){}

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.cuilUs = usuario?.cuil  
    });
      this.getDatos();
  }

  getDatos(){
    this.service.getDoms(this.cuilUs).subscribe({
      next:(data)=>{
        this.dataSource = data
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

  onDialogQuitar(domicilio: Domicilio){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: 'Quitar Domicilio',
        body: '¿Está seguro que desea eliminar su domicilio en '+ domicilio.calle + ' '+ domicilio.numero + '?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.eliminarDomicilio(domicilio.id)
        }
      }
    });
  }

  onDialogAgregarDomicilio(){
      let dialog = this.dialog.open(NuevoDomicilioComponent, {
        width: '700px'
      });
  
      dialog.afterClosed().subscribe(data => {
        if(data){
          this.getDatos();
        }
      })
  
    }

  eliminarDomicilio(id:number){
    let rq: DomicilioRq = new DomicilioRq();
    rq.idDomicilio =id;
    rq.usuario = this.cuilUs;
    this.service.eliminar(rq).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.openSnackBar('Domicilio eliminado', 'Cerrar');
          this.getDatos();
        } else{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: data.mensaje,
              cancelText: 'Cerrar',
            }
          });
        }
      }, error:(error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: 'Ocurrio un error interno al intentar eliminar el domicilio',
            cancelText: 'Cerrar',
          }
        });
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
}
