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

  form?:FormGroup;
  contact?: ContactDTO;
  errors: [] = [];

  ngOnInit(): void {
   this.initForm();
   const Id = this.route.snapshot.params['id'];

   if(Id){
    this.contactService.get(Id).subscribe((response) =>{
     this.contact = response;
     this.form!.patchValue(this.contact);
    },(erro: HttpErrorResponse)=>{
      console.log(erro.error);
    });
   } else {
    this.initForm();
   }

  }

  initForm() {
    this.form = this.formBuilder.group({
      id:[''],
      name:['', [Validators.required]],
      email:['',[Validators.required, Validators.email]],
      createdAt:['']
    });
  }

  save() {
    const contact = (this.form!.value);
    let request: Observable<ContactDTO>;

    // if(this.form?.invalid){
    //   this.form.markAllAsTouched();
    //   return;
    // }

    if(this.contact){
       request = this.contactService.update(contact.id, contact);
    } else{
      request = this.contactService.create(contact);
    }

    request.subscribe({
      next: ()=> {
        MessageService.sucessMessage();
        this.errors = [];
        this.router.navigate(['/']);
      },error: response =>{
        this.errors = response.error.errors;
      }
    });

  }

}
