import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { VeterinariesService } from '../../../services/Veterinaries-service';
import { Usuario } from '../../Usuario';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { GenericDialogComponent } from '../generic-dialog/generic-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-agregar-veterinario',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatButton,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './agregar-veterinario.component.html',
  styleUrl: './agregar-veterinario.component.scss'
})
export class AgregarVeterinarioComponent {
  form: FormGroup;
  usuario: any;

  private _snackBar = inject(MatSnackBar);

  mensaje:string="";

  constructor(
    private dialogRef: MatDialogRef<AgregarVeterinarioComponent>,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private service: VeterinariesService
  ) {
    this.form = this.fb.group({
      dni: new FormControl('',[Validators.required,Validators.minLength(7),Validators.maxLength(8)])
    });
  }

  buscarDatos(){
    this.service.buscarPorDni(this.form.get('dni')?.value).subscribe({
      next: (data)=>{
        this.usuario=data;
        if(this.usuario==null){
          this.mensaje = "No se encontró un profesional con el DNI indicado"
        }
      }, error:(error)=>{
        console.log(error);
      }
    });
  }

  agregar(){
    this.service.agregarProfesional(this.usuario.id).subscribe({
      next:(data)=>{
        if(data.estado!="ERROR"){
          this.openSnackBar('Profesional quitado', 'Cerrar');
          this.dialogRef.close(true);
        } else{
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
