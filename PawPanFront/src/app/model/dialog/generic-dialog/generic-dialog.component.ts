import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

export interface DialogData {
  type: 'normal' | 'error';
  title?: string;
  body: string;
  body2?:string
  acceptText: string;
  cancelText: string;
  onAccept: () => void;
}

@Component({
  selector: 'app-generic-dialog',
  standalone: true,
  imports:  [CommonModule, MatDialogModule, MatIconModule, MatButtonModule],
  templateUrl: './generic-dialog.component.html',
  styleUrl: './generic-dialog.component.scss'
})
export class GenericDialogComponent {
  constructor(
    private dialogRef: MatDialogRef<GenericDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  onAccept() {
    this.data.onAccept();
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
