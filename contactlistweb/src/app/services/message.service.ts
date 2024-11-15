import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  public static sucessMessage(){
    Swal.fire({
      title: "Finalizado!",
      text: "Operação realizada com sucesso!",
      icon: "success"
    });
  }

  public static errorDeleteMessage(item: string){
    const errorMessage = `Erro ao deletar ${item}`;
    Swal.fire({
      icon: "error",
      title: (errorMessage),
      text: "Operação não realizada!",
      footer: ''
    });
  }

  public static errorMessage(){
    Swal.fire({
      icon: "error",
      title: ("Falha ao realizar operação"),
      text: "Operação não realizada!",
      footer: ''
    });
  }


}
