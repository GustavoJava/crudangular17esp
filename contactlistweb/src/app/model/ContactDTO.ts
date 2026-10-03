import { EnderecoDTO } from './EnderecoDTO';

export interface ContactDTO {
  id: number;              // Opcional para a criação de um novo contato (onde o ID ainda não existe)
  name: string;
  email: string;
  createdAt?: string | Date; // Opcional no envio (gerado pelo backend) e aceita String ISO do JSON
  endereco?: EnderecoDTO;    // Opcional para permitir formulários em construção
}
