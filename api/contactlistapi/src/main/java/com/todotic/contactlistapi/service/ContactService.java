package com.todotic.contactlistapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.exception.RecordNotFoundException;
import com.todotic.contactlistapi.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ModelMapper mapper;
	

	public List<ContactDTO> findAll() {
		return this.contactRepository.findAll(Sort.by("name"))
				.stream().map(contact -> mapper.map(contact, ContactDTO.class)).collect(Collectors.toList());
	}

	public Contact findById(Integer id) {
		return this.contactRepository.findById(id)
				   .orElseThrow(()-> new RecordNotFoundException(id));
	}

	public Contact create(ContactDTO contactDTO) {
		contactDTO.setCreatedAt(LocalDateTime.now());
		Contact contact = mapper.map(contactDTO, Contact.class);
		
		contact.setName(contactDTO.getName());
		contact.setEmail(contactDTO.getEmail());
		contact.setCreatedAt(contactDTO.getCreatedAt());
		return this.contactRepository.save(contact);
	}

	public Contact update(ContactDTO contactDTO, Integer id) {
		return this.contactRepository.findById(id).map(contactUpdated -> {
			contactUpdated.setName(contactDTO.getName());
			contactUpdated.setEmail(contactDTO.getEmail());
			return contactRepository.save(contactUpdated);
		}).orElseThrow(()-> new RecordNotFoundException(id));

	}

	public void delete(Integer id) {
		this.contactRepository.findById(id)
			.orElseThrow(()-> new RecordNotFoundException(id));
		this.contactRepository.deleteById(id);
	}

}
