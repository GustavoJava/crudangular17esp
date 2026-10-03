package com.todotic.contactlistapi.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O nome é obrigatório")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail com formato inválido")
	@Column(name = "email", nullable = false)
	private String email;
		
	@NotNull(message = "A data de criação é obrigatória")
	@Column(name = "created_at", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime createdAt;
	
	@NotNull(message = "O endereço é obrigatório")
	@ManyToOne
	@JoinColumn(name = "endereco_id", nullable = false)
	private Endereco endereco;
}
