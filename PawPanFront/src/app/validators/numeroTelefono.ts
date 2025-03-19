import { AbstractControl, ValidationErrors } from '@angular/forms';
export function validacionTelefonoBasico(control: AbstractControl): ValidationErrors | null {
  const telefono = control.value;
  const telefonoRegex = /^[0-9]{8,12}$/; 

  if (telefono && !telefonoRegex.test(telefono)) {
    return { telefonoInvalido: true };
  }
  return null;
}