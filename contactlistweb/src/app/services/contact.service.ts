import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ContactDTO } from '../model/ContactDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private http = inject(HttpClient);
  private readonly BASE_URL = 'http://localhost:8080/api/contacts';

  constructor() { }

  list(): Observable<ContactDTO[]> {
   return this.http.get<ContactDTO[]>(this.BASE_URL);
  }

  get(id: number): Observable<ContactDTO> {
    return this.http.get<ContactDTO>(`${this.BASE_URL}/${id}`);
  }

  create(contact: ContactDTO): Observable<ContactDTO> {
    return this.http.post<ContactDTO>(`${this.BASE_URL}`, contact);
  }

  update(id: number, contact: ContactDTO): Observable<ContactDTO> {
   return this.http.put<ContactDTO>(`${this.BASE_URL}/${id}`, contact);
  }

  delete(id: number) {
   return this.http.delete<void>(`${this.BASE_URL}/${id}`);
  }

}
