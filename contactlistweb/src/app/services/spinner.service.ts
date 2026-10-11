import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SpinnerService {
  loading = signal(false);
}
