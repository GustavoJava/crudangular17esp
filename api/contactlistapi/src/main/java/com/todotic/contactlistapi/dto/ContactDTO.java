package com.todotic.contactlistapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDTO {
	
	private Integer id;
	
	@NotBlank(message = "Nome deve ser obrigatório")
	private String name;
	
	@Email(message = "Email inválido")
	@NotBlank(message = "Email deve ser obrigatório")
	private String email;
	
	private LocalDateTime createdAt;

	
}
		