import { AbstractControl, ValidationErrors } from '@angular/forms';

export function validacionContraseniasIguales(control: AbstractControl): ValidationErrors | null {
    const password = control.get('contrasenia')?.value;
    const confirmPassword = control.get('validarContrasenia')?.value;
  
    if (password && confirmPassword && password !== confirmPassword) {
      control.get('validarContrasenia')?.setErrors({ passwordsMismatch: true });
      return { passwordsMismatch: true };
    } else {
      const errors = control.get('validarContrasenia')?.errors;
      if (errors) {
        delete errors['passwordsMismatch'];
        if (Object.keys(errors).length === 0) {
          control.get('validarContrasenia')?.setErrors(null);
        } else {
          control.get('validarContrasenia')?.setErrors(errors);
        }
      }
    }
  
    return null;
  }
  