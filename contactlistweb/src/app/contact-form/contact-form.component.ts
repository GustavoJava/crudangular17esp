import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

// Imports do Angular Material
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIcon } from '@angular/material/icon';

import { ContactDTO } from '../model/ContactDTO';
import { ContactService } from '../services/contact.service';
import { MessageService } from '../services/message.service';

export const EMAIL_PATTERN = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [
    RouterModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTabsModule,
    MatIcon,
  ],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss',
})
export default class ContactFormComponent implements OnInit {
  private contactService = inject(ContactService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);
  private messageService = inject(MessageService);
  private http = inject(HttpClient);

  form!: FormGroup;
  contact?: ContactDTO;
  errors: string[] = [];

  ngOnInit(): void {
    this.initForm();
    const id = this.route.snapshot.params['id'];

    if (id) {
      this.contactService.get(id).subscribe({
        next: (response) => {
          this.contact = response;
          this.form.patchValue(this.contact);
        },
        error: (err: HttpErrorResponse) => {
          console.error(err.error);
        },
      });
    }
  }

  initForm(): void {
    this.form = this.formBuilder.group({
      id: [''],
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.pattern(EMAIL_PATTERN)]],
      createdAt: [''],
      endereco: this.formBuilder.group({
        id: [''],
        cep: ['', [Validators.required]],
        logradouro: ['', [Validators.required]],
        complemento: [''],
        unidade: [''],
        bairro: ['', [Validators.required]],
        localidade: ['', [Validators.required]],
        uf: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
        estado: ['', [Validators.required]],
        regiao: ['', [Validators.required]],
        ibge: [''],
        gia: [''],
        ddd: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
        siafi: ['']
      })
    });
  }

  consultarCep(event: any): void {
    const cep = event.target.value.replace(/\D/g, '');

    if (cep.length !== 8) {
      return;
    }

    this.http.get<any>(`https://viacep.com.br/ws/${cep}/json/`).subscribe({
      next: (dados) => {
        if (!dados.erro) {
          this.form.get('endereco')?.patchValue({
            logradouro: dados.logradouro,
            bairro: dados.bairro,
            localidade: dados.localidade,
            uf: dados.uf,
            estado: dados.estado,
            regiao: dados.regiao || 'Centro-Oeste',
            ibge: dados.ibge,
            gia: dados.gia,
            ddd: dados.ddd,
            siafi: dados.siafi
          });
        } else {
          this.messageService.errorMessage('CEP não encontrado.');
        }
      },
      error: () => {
        this.messageService.errorMessage('Erro ao consultar o CEP.');
      }
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const contactData: ContactDTO = this.form.value;
    let request: Observable<ContactDTO>;

    if (this.contact) {
      request = this.contactService.update(contactData.id, contactData);
    } else {
      request = this.contactService.create(contactData);
    }

    request.subscribe({
      next: () => {
        this.messageService.successMessage();
        this.errors = [];
        this.router.navigate(['/']);
      },
      error: (response) => {
        this.errors = response.error?.errors || [
          'Ocorreu um erro ao salvar o contato.',
        ];
      },
    });
  }
}
