package com.todotic.contactlistapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(Integer id) {
		super(String.format("Entidade com Id: %d não encontrado", id));
	}

}
