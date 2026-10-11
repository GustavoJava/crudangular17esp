import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { SpinnerService } from '../../services/spinner.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const spinner = inject(SpinnerService);
  spinner.loading.set(true);

  const startTime = Date.now();
  const minDuration = 500; // Tempo mínimo em milissegundos

  return next(req).pipe(
    finalize(() => {
      const elapsed = Date.now() - startTime;
      const remainingTime = Math.max(0, minDuration - elapsed);

      setTimeout(() => {
        spinner.loading.set(false);
      }, remainingTime);
    })
  );
};
