package com.todotic.contactlistapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.utils.DateUtils;

@Component
public class ContactMapper {
	
	@Autowired
    private EnderecoMapper enderecoMapper;

    public ContactDTO toDTO(Contact contact) {
        
    	if (contact == null) {
            return null;
        }

        return ContactDTO.builder()
            .id(contact.getId())
            .name(contact.getName())
            .email(contact.getEmail())
            .createdAt(contact.getCreatedAt())
            .endereco(enderecoMapper.toDTO(contact.getEndereco()))
            .build();
    }

    public Contact toEntity(ContactDTO contactDTO) {
        
    	if (contactDTO == null) {
            return null;
        }

        return Contact.builder()
            .id(contactDTO.getId())
            .name(contactDTO.getName())
            .email(contactDTO.getEmail())
            .createdAt(contactDTO.getCreatedAt() != null ? contactDTO.getCreatedAt() : DateUtils.getHoje())
            .endereco(enderecoMapper.toEntity(contactDTO.getEndereco()))
            .build();
    }
}