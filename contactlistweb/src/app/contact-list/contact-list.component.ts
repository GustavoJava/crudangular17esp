import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';

import { ContactDTO } from '../model/ContactDTO';
import { ContactService } from '../services/contact.service';
import { MessageService } from '../services/message.service';
import { ConfirmDialogComponent } from '../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [
    DatePipe,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './contact-list.component.html',
  styleUrl: './contact-list.component.scss'
})
export default class ContactListComponent implements OnInit {

  private contactService = inject(ContactService);
  private dialog = inject(MatDialog);
  private messageService = inject(MessageService); // Injeção direta em 1 linha

  contacts: ContactDTO[] = [];
  displayedColumns: string[] = ['id', 'name', 'email', 'createdAt', 'actions'];

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

  delete(contact: ContactDTO): void {
     this.contactService.delete(contact.id).subscribe({
      next: () => {
        this.messageService.successMessage();
        this.loadAll();
      },
      error: (erro: HttpErrorResponse) => {
        this.messageService.errorMessage();
        console.error(erro.error);
      }
    });
  }

  confirmDelete(contact: ContactDTO): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '430px',
      data: {
        title: 'Confirmar exclusão',
        message: `Deseja realmente excluir ${contact.name}?`,

      }
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.delete(contact);
      }
    });
  }

}
