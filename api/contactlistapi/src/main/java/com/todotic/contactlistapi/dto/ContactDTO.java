package com.todotic.contactlistapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactDTO(
		Integer id,
		@NotBlank(message = "Nome deve ser obrigatório") String name,
		@Email(message = "Email inválido") 
		@NotBlank(message = "Email deve ser obrigatório") String email,
		LocalDateTime createdAt
		) {
	
}
	
		