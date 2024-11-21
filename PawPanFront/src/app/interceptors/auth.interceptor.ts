import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = localStorage.getItem('authToken'); // Recupera el token desde localStorage

  if (authToken) {
    req = req.clone({
      setHeaders: {
        Authorization: `Pawplan ${authToken}`
      }
    });
  }

  return next(req); // Continúa con la solicitud
};

