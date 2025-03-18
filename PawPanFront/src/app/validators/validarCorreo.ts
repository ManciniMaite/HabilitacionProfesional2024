import { AbstractControl, ValidationErrors } from '@angular/forms';

export function validacionFormatoCorreo(control: AbstractControl): ValidationErrors | null {
    const email = control.value;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
    if (email && !emailRegex.test(email)) {
      return { emailInvalido: true };
    }
    return null;
  }