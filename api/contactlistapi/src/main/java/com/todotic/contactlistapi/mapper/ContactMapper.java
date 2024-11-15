package com.todotic.contactlistapi.mapper;

import org.springframework.stereotype.Component;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.utils.DateUtils;

@Component
public class ContactMapper {
	
	public ContactDTO toDTO(Contact contact) {
        if (contact == null) {
            return null;
        }
        
        return new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getCreatedAt());
    }
	
	public Contact toEntity(ContactDTO contact) {
        if (contact == null) {
            return null;
        }
        
        return new Contact(contact.id(), contact.name(), contact.email(), DateUtils.getHoje());
    }



}
