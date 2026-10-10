package com.todotic.contactlistapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/contacts")
@CrossOrigin("*")
//@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.100.4:4200"})
public class ContactController {

	@Autowired
	private ContactService contactService;

	@GetMapping
	public List<ContactDTO> list() {
		return this.contactService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactDTO> findById(@PathVariable Integer id) {
		ContactDTO contactDTO = contactService.findById(id);
		return ResponseEntity.ok(contactDTO);
	}

	@PostMapping
	public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO contactoDTO) {
		ContactDTO savedContact = contactService.create(contactoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<ContactDTO> update(@PathVariable Integer id, @Valid @RequestBody ContactDTO contactoDTO) {
		ContactDTO updatedContact = this.contactService.update(contactoDTO, id);
		return ResponseEntity.ok(updatedContact); // Retorna status 200 OK com o DTO no body
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.contactService.delete(id);
		return ResponseEntity.noContent().build(); // Retorna status 204 No Content sem corpo
	}
}
