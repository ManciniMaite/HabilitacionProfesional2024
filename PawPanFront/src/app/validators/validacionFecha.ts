import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function validacionFecha(minAge: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const birthDate = control.value;
    if (!birthDate) {
      return null; // No validar si no hay fecha
    }

    const today = new Date();
    const birth = new Date(birthDate);

    let age = today.getFullYear() - birth.getFullYear();
    const monthDiff = today.getMonth() - birth.getMonth();
    const dayDiff = today.getDate() - birth.getDate();

    // Ajustamos si todavía no cumplió años este año
    if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
      age--;
    }

    return age >= minAge ? null : { minAge: { requiredAge: minAge, actualAge: age } };
  };
}
