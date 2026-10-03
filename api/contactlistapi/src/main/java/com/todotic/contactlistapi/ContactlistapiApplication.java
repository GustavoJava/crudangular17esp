package com.todotic.contactlistapi;

import java.util.List;
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.entity.Endereco;
import com.todotic.contactlistapi.repository.ContactRepository;
import com.todotic.contactlistapi.repository.EnderecoRepository;
import com.todotic.contactlistapi.utils.DateUtils;

@SpringBootApplication
public class ContactlistapiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC-3"));
		SpringApplication.run(ContactlistapiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(ContactRepository contactRepository, EnderecoRepository enderecoRepository) {
		return args -> {
			// Massa de teste para Endereços
			Endereco endSP = new Endereco(
				null, "01310-100", "Avenida Paulista", "Conjunto 501", "Torre A", 
				"Bela Vista", "São Paulo", "SP", "São Paulo", "Sudeste", 
				"3550308", "1004", "11", "7107"
			);
			
			Endereco endRJ = new Endereco(
				null, "22041-001", "Avenida Atlântica", "Apto 802", null, 
				"Copacabana", "Rio de Janeiro", "RJ", "Rio de Janeiro", "Sudeste", 
				"3304557", "6001", "21", "6001"
			);
			
			Endereco endMG = new Endereco(
				null, "30130-100", "Avenida Afonso Pena", "Bloco B - Sala 12", null, 
				"Boa Viagem", "Belo Horizonte", "MG", "Minas Gerais", "Sudeste", 
				"3106200", "0620", "31", "4123"
			);
			
			Endereco endDF = new Endereco(
				null, "70040-010", "Eixo Monumental", "Via N1", "Lote 2", 
				"Zona Cívico-Administrativa", "Brasília", "DF", "Distrito Federal", "Centro-Oeste", 
				"5300108", null, "61", "9701"
			);

			// Salva os endereços antes de associar aos contatos
			enderecoRepository.saveAll(List.of(endSP, endRJ, endMG, endDF));

			// Massa de teste para Contatos
			List<Contact> contatos = List.of(
				new Contact(null, "Carlos Eduardo Silva", "carlos.silva@email.com", DateUtils.getHoje(), endSP),
				new Contact(null, "Mariana Oliveira", "mariana.oliveira@email.com", DateUtils.getHoje(), endRJ),
				new Contact(null, "Fernando Souza", "fernando.souza@email.com", DateUtils.getHoje(), endMG),
				new Contact(null, "Beatriz Mendes", "beatriz.mendes@email.com", DateUtils.getHoje(), endDF),
				new Contact(null, "Lucas Pereira", "lucas.pereira@email.com", DateUtils.getHoje(), endSP), 
				new Contact(null, "Camila Rodrigues", "camila.rodrigues@email.com", DateUtils.getHoje(), endRJ) 
			);

			contactRepository.saveAll(contatos);	
		};
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}