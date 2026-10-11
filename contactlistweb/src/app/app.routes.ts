import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./contact-list/contact-list.component')
  },
  {
    path: 'new',
    loadComponent: () => import('./contact-form/contact-form.component'),
    data: { acao: 'create' } // Opcional, caso queira padronizar
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./contact-form/contact-form.component'),
    data: { acao: 'edit' } // Passando 'edit' como parâmetro de rota
  },
  {
    path: 'view/:id',
    loadComponent: () => import('./contact-form/contact-form.component'),
    data: { acao: 'view' } // Passando 'view' como parâmetro de rota
  }
];
