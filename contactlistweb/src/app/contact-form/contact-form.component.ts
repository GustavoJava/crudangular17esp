import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

import { ContactDTO } from '../model/ContactDTO';
import { EnderecoDTO } from '../model/EnderecoDTO';
import { ContactService } from '../services/contact.service';
import { EnderecoService } from '../services/endereco.service';
import { MessageService } from '../services/message.service';
import { CEP_PATTERN, EMAIL_PATTERN } from '../shared/utils/constants';

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
  private enderecoService = inject(EnderecoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);
  private messageService = inject(MessageService);

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
        cep: ['', [Validators.required, Validators.pattern(CEP_PATTERN)]],
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

    this.enderecoService.findByCep(cep).subscribe({
      next: (endereco: EnderecoDTO) => {
        if (!endereco.erro) {
          this.form.get('endereco')?.patchValue(endereco);
        } else {
          this.messageService.errorMessage(`CEP ${cep} não encontrado!`);
        }
      },
      error: () => {
        this.messageService.errorMessage(`Erro ao consultar o CEP ${cep}.`);
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
