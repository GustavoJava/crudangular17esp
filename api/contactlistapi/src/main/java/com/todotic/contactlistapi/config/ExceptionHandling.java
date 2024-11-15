package com.todotic.contactlistapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todotic.contactlistapi.exception.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandling {
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleNotFoundException(RecordNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
		
		ProblemDetail problemDetail =  ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		
		List<String> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getFieldErrors();
		
		for (FieldError error: fieldErrors) {
			errors.add(error.getDefaultMessage());
		}
		
		problemDetail.setProperty("errors", errors);
		
		return problemDetail;
	}

}
