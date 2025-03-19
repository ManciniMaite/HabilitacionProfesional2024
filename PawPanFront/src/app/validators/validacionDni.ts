import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
// export function validacionDni(control: AbstractControl, tipo: string): ValidationErrors | null {
//   const dni = control.value;
//   var dniRegex;
//   if(tipo=='dni'){
//     dniRegex = /^[0-9]{7,8}$/;
//   } else {
//     dniRegex = /^[0-9]{10,11}$/;
//   }

//   if (dni && !dniRegex.test(dni)) {
//     return { dniInvalido: true };
//   }
//   return null;
// }

export function validacionDni( tipo: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const dni = control.value;
    var dniRegex;
    if(tipo=='dni'){
      dniRegex = /^[0-9]{7,8}$/;
    } else {
      dniRegex = /^[0-9]{10,11}$/;
    }

    if (dni && !dniRegex.test(dni)) {
      return { dniInvalido: true };
    }
    return null;
  };
}
