import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService); // Inyectar el servicio manualmente
  let token: string | null = null;

  authService.usuario$.subscribe(usuario => {
    token = usuario?.token || null; // Obtener el token del BehaviorSubject
  });

  if (token) {
    console.log('Agregando token:', token);
    const cloned = req.clone({
      setHeaders: { Authorization: `${token}` }
    });
    return next(cloned);
  }

  console.log('No se encontró token');
  return next(req);
};
