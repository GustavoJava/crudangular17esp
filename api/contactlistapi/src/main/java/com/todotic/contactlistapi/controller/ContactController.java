package com.todotic.contactlistapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.service.ContactService;

@RestController
@RequestMapping(value = "/api/contacts")
@CrossOrigin("*")
public class ContactController {

	@Autowired
	private ContactService contactService;

	@GetMapping
	public List<ContactDTO> list() {
		return this.contactService.findAll();
	}

	@GetMapping(value = "/{id}")
	public Contact get(@PathVariable Integer id) {
		return this.contactService.findById(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Contact create(@Validated @RequestBody ContactDTO contactoDTO) {
		return this.contactService.create(contactoDTO);
	}

	@PutMapping(value = "{id}")
	public Contact update(@PathVariable Integer id,
						  @Validated @RequestBody ContactDTO contactoDTO) {
		return this.contactService.update(contactoDTO, id);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		this.contactService.delete(id);
	}

}
