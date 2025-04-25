import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Especie } from '../../Especie';
import { Raza } from '../../Raza';
import { AnimalRq } from '../../AnimalRq';
import { EspecieService } from '../../../services/especie.service';
import { RazaService } from '../../../services/raza.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { GenericDialogComponent } from '../generic-dialog/generic-dialog.component';
import { AnimalService } from '../../../services/animal.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker'; 
import { MatNativeDateModule, NativeDateAdapter } from '@angular/material/core';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MAT_DATE_LOCALE, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';

@Component({
  selector: 'app-agregar-animal',
  standalone: true,
  imports: [
    MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatOptionModule,
    MatSelectModule,
    MatNativeDateModule
  ],
  providers: [
    { provide: DateAdapter, useClass: NativeDateAdapter },
    { provide: MAT_DATE_LOCALE, useValue: 'es-AR' }
  ],
  templateUrl: './agregar-animal.component.html',
  styleUrl: './agregar-animal.component.scss'
})
export class AgregarAnimalComponent implements OnInit{
  private dateAdapter = inject(DateAdapter);

  idUs: number;

  mascota: FormGroup;
  especies: Especie[] = [];
  razas: Raza[] = [];

  private _snackBar = inject(MatSnackBar);

  constructor(
    private fb: FormBuilder,
    private especieService: EspecieService,
    private razasService: RazaService,
    private dialog: MatDialog,
    private service: AnimalService,
    private dialogRef: MatDialogRef<AgregarAnimalComponent>,
  ){
    this.mascota = this.fb.group({
          nombreMascota:    new FormControl('', Validators.required),
          foto:             new FormControl(''),
          fechaNacMascota:  new FormControl(''),
          especie:          new FormControl('', Validators.required),
          raza:             new FormControl('', Validators.required),
          peso:             new FormControl('')
        });
  }

  ngOnInit(): void {
    this.dateAdapter.setLocale('es-AR');
    this.getEspecies();
  }

  getEspecies(){
    this.especieService.getAll().subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.especies = data.especies;
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
            body: 'Ocurrio un problema al buscar las especies de animales',
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getRazas(id: number){
    this.razasService.getAll(id).subscribe({
      next:(data)=> {
          if(data.estado != "ERROR"){
            this.razas = data.razas;
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
            body: 'Ocurrio un problema al buscar las especies de animales',
            cancelText: 'Cerrar'
          }
        });
        console.log(error);
      }
    });
  }

  getObject():AnimalRq{
    let animal: AnimalRq = new AnimalRq();
    animal.nombre = this.mascota.get('nombreMascota')?.value;
    animal.fechaNac = this.mascota.get('fechaNacMascota')?.value;
    animal.razaId=this.mascota.get('raza')?.value.id;
    animal.peso=this.mascota.get('peso')?.value;

    return animal;
  }

  onSave(){
    let rq: AnimalRq = this.getObject();

    this.service.crearAnimal(rq).subscribe({
      next:(data)=>{
        if(data.estado!='ERROR'){
          this.openSnackBar('Animal agregado!', 'Cerrar');
          this.dialogRef.close(true);
        }else{
          this.dialog.open(GenericDialogComponent, {
            data: {
              type: 'error',
              title: 'Algo salió mal',
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
            title: 'Algo salio mal!',
            body: 'por favor reintentá',
            cancelText: 'Cerrar'
          }
        });
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
