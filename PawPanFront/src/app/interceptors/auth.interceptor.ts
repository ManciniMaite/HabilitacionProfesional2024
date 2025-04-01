import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const usuario = authService.getUsuarioActual(); // Obtiene el usuario correctamente
  const authToken = usuario?.token; // Ahora `usuario` tiene la propiedad `token`

  if (authToken) {
    req = req.clone({
      setHeaders: {
        Authorization: `${authToken}`,
        'Content-Type': 'application/json'
      }
    });
  }

  return next(req);
};
