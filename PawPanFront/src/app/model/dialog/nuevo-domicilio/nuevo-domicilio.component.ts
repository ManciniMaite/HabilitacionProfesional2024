import { Component, inject, OnInit } from '@angular/core';
import { CiudadService } from '../../../services/ciudad.service';
import { Ciudad } from '../../Ciudad';
import { GenericDialogComponent } from '../generic-dialog/generic-dialog.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { DomicilioRq } from '../../DomicilioRq';
import { DomicilioService } from '../../../services/Domicilio.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-nuevo-domicilio',
  standalone: true,
  imports: [
    MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule,
  ],
  templateUrl: './nuevo-domicilio.component.html',
  styleUrl: './nuevo-domicilio.component.scss'
})
export class NuevoDomicilioComponent implements OnInit{
  private _snackBar = inject(MatSnackBar);
  authService = inject(AuthService); 
  cuilUs: string;
  ciudades: Ciudad[];

  form: FormGroup;

  constructor(
    private ciudadesService: CiudadService,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<NuevoDomicilioComponent>,
    private fb: FormBuilder,
    private domicilioService: DomicilioService
  ){
    this.form = this.fb.group({
        ciudad:    new FormControl('', Validators.required),
        calle:     new FormControl('', Validators.required),
        numero:    new FormControl('', Validators.required)
      });
  }
  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.cuilUs = usuario?.cuil  
    });
    this.getCiudades();
  }

  onDialogSave(){
    this.dialog.open(GenericDialogComponent, {
      data: {
        type: 'normal',
        title: '¡Agregar Domicilio!',
        body: '¿Estás segura de que querés continuar?',
        acceptText: 'Sí, continuar',
        cancelText: 'Cancelar',
        onAccept: () => {
          this.save();
        }
      }
    });
  }

  save(){ 
    let rq: DomicilioRq = new DomicilioRq()
    rq.ciudadId=this.form.get('ciudad')?.value;
    rq.calle=this.form.get('calle')?.value;
    rq.numero=this.form.get('numero')?.value;
    rq.usuario=this.cuilUs;

    this.domicilioService.agregar(rq).subscribe({
      next:(data)=> {
        if(data){
          this.openSnackBar('Domicilio agregado!', 'Cerrar');
          this.dialogRef.close(true);
        } else {
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: '¡Algo salió mal!',
              body: "Ocurrio un error al agregar el domicilio",
              cancelText: 'Cerrar'
            }
          });
        }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error interno al guardar el domicilio",
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getCiudades(){
    this.ciudadesService.getAll().subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.ciudades = data.ciudades;
          } else {
            this.dialog.open(GenericDialogComponent, {
              data: {
                type: 'error',
                title: '¡Algo salió mal!',
                body: data.mensaje,
                cancelText: 'Cerrar'
              }
            });
            console.log(data.mensaje);
          }
      }, error: (error)=>{
        this.dialog.open(GenericDialogComponent, {
          data: {
            type: 'error',
            title: '¡Algo salió mal!',
            body: "Ocurrio un error interno al obtener las ciudades",
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  close(){
    this.dialogRef.close();
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}
