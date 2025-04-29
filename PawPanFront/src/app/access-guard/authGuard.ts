import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';; 
import { map, take } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.usuario$.pipe(
    take(1), // Tomamos solo el último valor
    map(usuario => {
      console.log('usuario: ', usuario)
      if (usuario) {
        return true;
      } else {
        router.navigate(['/iniciar-sesion']);
        return false;
      }
    })
  );
};
