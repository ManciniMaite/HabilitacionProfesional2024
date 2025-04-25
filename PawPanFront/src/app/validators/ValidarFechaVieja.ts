import { AbstractControl, ValidationErrors } from '@angular/forms';

export function fechaDesdeHoyValidator(control: AbstractControl): ValidationErrors | null {
  const fecha = new Date(control.value);
  const hoy = new Date();
  hoy.setHours(0, 0, 0, 0); // quitar hora

  return fecha < hoy ? { fechaInvalida: true } : null;
}