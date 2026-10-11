import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EnderecoDTO } from '../model/EnderecoDTO';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {

  private http = inject(HttpClient);
  private readonly VIACEP_API_URL = 'https://viacep.com.br/ws';

  constructor() { }

  findByCep(cep: string): Observable<EnderecoDTO> {
    return this.http.get<EnderecoDTO>(`${this.VIACEP_API_URL}/${cep}/json/`);
  }

}
