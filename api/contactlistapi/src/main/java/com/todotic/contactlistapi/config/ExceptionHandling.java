package com.todotic.contactlistapi.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todotic.contactlistapi.exception.RecordNotFoundException;

@RestControllerAdvice
public class ExceptionHandling {

    // 1. Tratamento para Recursos Não Encontrados (404)
    @ExceptionHandler(RecordNotFoundException.class)
    public ProblemDetail handleNotFoundException(RecordNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso Não Encontrado");
        return problemDetail;
    }

    // 2. Tratamento para Validações do @Valid / Jakarta Validation (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, 
            "Um ou mais campos estão inválidos. Preencha corretamente e tente novamente."
        );
        problemDetail.setTitle("Erro de Validação");

        // Associa cada campo com sua respectiva mensagem de erro
        Map<String, String> fieldErrorsMap = new HashMap<>();
        for (FieldError error : exception.getFieldErrors()) {
            fieldErrorsMap.put(error.getField(), error.getDefaultMessage());
        }

        problemDetail.setProperty("errors", fieldErrorsMap);

        return problemDetail;
    }
}