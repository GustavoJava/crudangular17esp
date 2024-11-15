package com.todotic.contactlistapi;

import java.util.List;
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.repository.ContactRepository;
import com.todotic.contactlistapi.utils.DateUtils;

@SpringBootApplication
public class ContactlistapiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC-3")); // add this code
		SpringApplication.run(ContactlistapiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(ContactRepository repository) {
		return args -> {
			List<Contact> list = List.of(
					new Contact("Abba Briks","abba@gmail.com", DateUtils.getHoje()),
					new Contact("Mary Lee","lee@gmail.com", DateUtils.getHoje()),
					new Contact("Jerry Hawks","hawks@gmail.com", DateUtils.getHoje()),
					new Contact("John Malkovich","john@gmail.com", DateUtils.getHoje()),
					new Contact("Salud Abdala","abdala@gmail.com", DateUtils.getHoje()),
					new Contact("Shin Hati","hati@gmail.com", DateUtils.getHoje()),
					new Contact("Jennifer Aniston","anistom@gmail.com", DateUtils.getHoje()),
					new Contact("Karin Boyler","boyler@gmail.com", DateUtils.getHoje())
			);
			repository.saveAll(list);	
		};
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
//	private String converterData(LocalDateTime data){
//		LocalDateTime agora = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        System.out.println("LocalDateTime antes de formatar: " + agora);
//
//        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//
//        String agoraFormatado = agora.format(formatter);
//
//        System.out.println("LocalDateTime depois de formatar: " + agoraFormatado);
//        
//        return agoraFormatado;
//	}

}
