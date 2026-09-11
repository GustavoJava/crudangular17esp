import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

import { ContactDTO } from '../model/ContactDTO';
import { ContactService } from '../services/contact.service';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [DatePipe, RouterModule],
  templateUrl: './contact-list.component.html',
  styleUrl: './contact-list.component.scss'
})
export default class ContactListComponent implements OnInit {

  private contactService = inject(ContactService);

  contacts: ContactDTO[] = [];

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.contactService.list().subscribe({
      next: (contacts: ContactDTO[]) => {
        this.contacts = contacts;
      },
      error: (err: HttpErrorResponse) => {
        console.error('Erro ao carregar contatos', err);
      }
    });
  }

  confirmDelete(contact: ContactDTO): void {
    Swal.fire({
      title: `Deseja realmente remover ${contact.name}?`,
      text: 'Operação não poderá ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      confirmButtonText: 'Sim',
      cancelButtonText: 'Cancelar',
      focusCancel: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.delete(contact);
      }
    });
  }

  delete(contact: ContactDTO): void {
    this.contactService.delete(contact.id).subscribe({
      next: () => {
        MessageService.sucessMessage();
        this.loadAll();
      },
      error: (erro: HttpErrorResponse) => {
        MessageService.errorDeleteMessage(contact.name);
        console.error(erro.error);
      }
    });
  }
}
