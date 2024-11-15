package com.todotic.contactlistapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.exception.RecordNotFoundException;
import com.todotic.contactlistapi.mapper.ContactMapper;
import com.todotic.contactlistapi.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactMapper contactMapper;
	

	public List<ContactDTO> findAll() {
		return this.contactRepository.findAll(Sort.by("name"))
				   .stream().map(this.contactMapper::toDTO)
				   .collect(Collectors.toList());
	}

	public ContactDTO findById(Integer id) {
		return this.contactRepository.findById(id)
				   .map(this.contactMapper::toDTO)
				   .orElseThrow(()-> new RecordNotFoundException(id));
	}

	public ContactDTO create(ContactDTO contactDTO) {
		Contact contact = this.contactMapper.toEntity(contactDTO);
		contact.setCreatedAt(LocalDateTime.now());
		return this.contactMapper.toDTO(this.contactRepository.save(contact));
	}

	public ContactDTO update(ContactDTO contactDTO, Integer id) {
		return this.contactRepository.findById(id).map(contactUpdated -> {
			contactUpdated.setName(contactDTO.name());
			contactUpdated.setEmail(contactDTO.email().toLowerCase());
			return this.contactMapper.toDTO(contactRepository.save(contactUpdated));
		}).orElseThrow(()-> new RecordNotFoundException(id));

	}

	public void delete(Integer id) {
		this.contactRepository.findById(id)
			.orElseThrow(()-> new RecordNotFoundException(id));
		this.contactRepository.deleteById(id);
	}

}
