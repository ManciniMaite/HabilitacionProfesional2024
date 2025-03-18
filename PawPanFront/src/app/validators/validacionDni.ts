import { AbstractControl, ValidationErrors } from '@angular/forms';
export function validacionDni(control: AbstractControl): ValidationErrors | null {
  const dni = control.value;
  const dniRegex = /^[0-9]{7,8}$/; 

  if (dni && !dniRegex.test(dni)) {
    return { dniInvalido: true };
  }
  return null;
}