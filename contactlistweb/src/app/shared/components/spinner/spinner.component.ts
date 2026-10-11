import { Component, inject } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SpinnerService } from '../../../services/spinner.service';

@Component({
  selector: 'app-spinner',
  standalone: true,
  imports: [MatProgressSpinnerModule],
  template: `
    @if (spinner.loading()) {
      <div class="overlay">
        <mat-spinner diameter="120"></mat-spinner>
      </div>
    }
  `,
  styles: [`
    .overlay {
      position: fixed;
      inset: 0;
      background: rgba(0, 0, 0, 0.3);
      display: grid;
      place-items: center;
      z-index: 9999;
    }
  `]
})
export class SpinnerComponent {
  spinner = inject(SpinnerService);
}
