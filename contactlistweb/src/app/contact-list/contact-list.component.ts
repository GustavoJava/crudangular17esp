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
export default class ContactListComponent implements OnInit{

  private contactService = inject(ContactService);

  contacts: ContactDTO[] = [];

  ngOnInit(): void {
   this.loadAll();
  }

  loadAll(){
    this.contactService.list().subscribe((contacts: ContactDTO[]) =>{
      this.contacts = contacts;
    });
  }

  confirmDelete(contact:ContactDTO){
    const message = (`Deseja realmente remover ${contact.name} ?`);

    Swal.fire({
      title: (message),
      text: ('Operação não poderá ser desfeita!'),
      icon: ('warning'),
      showCancelButton: true,
      confirmButtonColor: ('#d33'),
      confirmButtonText: ('Sim'),
      cancelButtonText: ('Cancelar'),
      focusCancel: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.delete(contact);
        MessageService.sucessMessage();
      }
    });
  }

  delete(contact:ContactDTO) {
    const errorMessage = (`Erro ao deletar ${contact.name}`);

    this.contactService.delete(contact.id).subscribe(()=>{
     this.loadAll();
    },(erro: HttpErrorResponse)=>{
      MessageService.errorDeleteMessage(contact.name);
      console.log(erro.error);
    });
  }

  getDate(value: any): Date {
    var datePipe = new DatePipe('pt-BR');
    value = datePipe.transform('17/09/2024', 'dd/MM/yyyy');
    return value
  }

}
