dica de uso
Para rodar no localhost: Mantenha o valor do environment.development.ts
como http://localhost:8080/api e execute ng serve.

Para rodar no celular (rede local):
Altere a URL do environment.development.ts para o
seu IP ([http://192.168.100.4:8080/api](http://192.168.100.4:8080/api)) e
execute
ng serve --host 0.0.0.0


===============================================

qual seria melhor forma de fazer um filtro tanto no spring boot e no angular front-end? segue:

import { DatePipe } from '@angular/common';

import { HttpErrorResponse } from '@angular/common/http';

import { Component, inject, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';

import Swal from 'sweetalert2';



// Imports do Angular Material

import { MatButtonModule } from '@angular/material/button';

import { MatCardModule } from '@angular/material/card';

import { MatIconModule } from '@angular/material/icon';

import { MatTableModule } from '@angular/material/table';

import {MatDialog, MatDialogModule} from '@angular/material/dialog';



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

width: '400px',

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

e:

<div class="list-container">

<div class="header-action">

<h2>Contatos</h2>

<button mat-raised-button color="primary" routerLink="/new">

<mat-icon>add</mat-icon> Novo Contato

</button>

</div>



@if (contacts.length > 0) {

<!-- VISÃO DESKTOP / TABLET (MatTable) -->

<div class="table-responsive d-none d-md-block mat-elevation-z2">

<table mat-table [dataSource]="contacts" class="w-100">



<!-- ID Column -->

<ng-container matColumnDef="id">

<th mat-header-cell *matHeaderCellDef> #ID </th>

<td mat-cell *matCellDef="let contact"> {{ contact.id }} </td>

</ng-container>



<!-- Name Column -->

<ng-container matColumnDef="name">

<th mat-header-cell *matHeaderCellDef> Nome </th>

<td mat-cell *matCellDef="let contact"> <strong>{{ contact.name }}</strong> </td>

</ng-container>



<!-- Email Column -->

<ng-container matColumnDef="email">

<th mat-header-cell *matHeaderCellDef> E-mail </th>

<td mat-cell *matCellDef="let contact"> {{ contact.email }} </td>

</ng-container>



<!-- CreatedAt Column -->

<ng-container matColumnDef="createdAt">

<th mat-header-cell *matHeaderCellDef> Criado em </th>

<td mat-cell *matCellDef="let contact"> {{ contact.createdAt | date : 'dd/MM/yyyy HH:mm' }} </td>

</ng-container>



<!-- Actions Column -->

<ng-container matColumnDef="actions">

<th mat-header-cell *matHeaderCellDef class="text-end"> Ações </th>

<td mat-cell *matCellDef="let contact" class="text-end">

<button mat-icon-button color="primary" [routerLink]="['edit', contact.id]">

<mat-icon>edit</mat-icon>

</button>

<button mat-icon-button color="warn" (click)="confirmDelete(contact)">

<mat-icon>delete</mat-icon>

</button>

</td>

</ng-container>



<tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>

<tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>

</table>

</div>



<!-- VISÃO CELULAR (Cards Material) -->

<div class="cards-mobile d-md-none">

@for (contact of contacts; track contact.id) {

<mat-card class="mb-3">

<mat-card-header>

<mat-card-title>{{ contact.name }}</mat-card-title>

<mat-card-subtitle>#{{ contact.id }}</mat-card-subtitle>

</mat-card-header>

<mat-card-content class="mt-2">

<p><strong>E-mail:</strong> {{ contact.email }}</p>

<p><strong>Criado em:</strong> {{ contact.createdAt | date : 'dd/MM/yyyy HH:mm' }}</p>

</mat-card-content>

<mat-card-actions align="end">

<button mat-button color="primary" [routerLink]="['edit', contact.id]">

EDITAR

</button>

<button mat-button color="warn" (click)="confirmDelete(contact)">

DELETAR

</button>

</mat-card-actions>

</mat-card>

}

</div>

} @else {

<div class="empty-message">

Nenhum contato encontrado.

</div>

}

</div>

e:

package com.todotic.contactlistapi.controller;



import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestController;



import com.todotic.contactlistapi.dto.ContactDTO;

import com.todotic.contactlistapi.service.ContactService;



@RestController

@RequestMapping(value = "/api/contacts")

//@CrossOrigin("*")

@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.100.4:4200"})

public class ContactController {



@Autowired

private ContactService contactService;



@GetMapping

public List<ContactDTO> list() {

return this.contactService.findAll();

}



@GetMapping(value = "/{id}")

public ContactDTO get(@PathVariable Integer id) {

return this.contactService.findById(id);

}



@PostMapping

@ResponseStatus(code = HttpStatus.CREATED)

public ContactDTO create(@Validated @RequestBody ContactDTO contactoDTO) {

return this.contactService.create(contactoDTO);

}



@PutMapping(value = "{id}")

public ContactDTO update(@PathVariable Integer id,

     @Validated @RequestBody ContactDTO contactoDTO) {

return this.contactService.update(contactoDTO, id);

}



@DeleteMapping(value = "/{id}")

@ResponseStatus(code = HttpStatus.NO_CONTENT)

public void delete(@PathVariable Integer id) {

this.contactService.delete(id);

}



}

e:

package com.todotic.contactlistapi.service;



import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;



import com.todotic.contactlistapi.dto.ContactDTO;

import com.todotic.contactlistapi.entity.Contact;

import com.todotic.contactlistapi.exception.RecordNotFoundException;

import com.todotic.contactlistapi.mapper.ContactMapper;

import com.todotic.contactlistapi.repository.ContactRepository;



@Service

public class ContactService {



@Autowired

private ContactRepository contactRepository;


@Autowired

private ContactMapper contactMapper;




public List<ContactDTO> findAll() {

return this.contactRepository.findAll(Sort.by("name"))

   .stream().map(this.contactMapper::toDTO)

   .collect(Collectors.toList());

}



public ContactDTO findById(Integer id) {

return this.contactRepository.findById(id)

   .map(this.contactMapper::toDTO)

   .orElseThrow(()-> new RecordNotFoundException(id));

}



public ContactDTO create(ContactDTO contactDTO) {

Contact contact = this.contactMapper.toEntity(contactDTO);

contact.setCreatedAt(LocalDateTime.now());

return this.contactMapper.toDTO(this.contactRepository.save(contact));

}



public ContactDTO update(ContactDTO contactDTO, Integer id) {

return this.contactRepository.findById(id).map(contactUpdated -> {

contactUpdated.setName(contactDTO.name());

contactUpdated.setEmail(contactDTO.email().toLowerCase());

return this.contactMapper.toDTO(contactRepository.save(contactUpdated));

}).orElseThrow(()-> new RecordNotFoundException(id));



}



public void delete(Integer id) {

this.contactRepository.findById(id)

.orElseThrow(()-> new RecordNotFoundException(id));

this.contactRepository.deleteById(id);

}



}

podemos utilizar paginação também

