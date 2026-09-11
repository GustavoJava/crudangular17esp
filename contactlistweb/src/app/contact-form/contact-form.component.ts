import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

import { ContactDTO } from '../model/ContactDTO';
import { ContactService } from '../services/contact.service';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export default class ContactFormComponent implements OnInit {

  private contactService = inject(ContactService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

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
        }
      });
    }
  }

  initForm(): void {
    this.form = this.formBuilder.group({
      id: [''],
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      createdAt: ['']
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
        MessageService.sucessMessage();
        this.errors = [];
        this.router.navigate(['/']);
      },
      error: (response) => {
        this.errors = response.error?.errors || ['Ocorreu um erro ao salvar o contato.'];
      }
    });
  }

}
