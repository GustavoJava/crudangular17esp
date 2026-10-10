package com.todotic.contactlistapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;
import org.springframework.stereotype.Service;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.entity.Endereco;
import com.todotic.contactlistapi.exception.RecordNotFoundException;
import com.todotic.contactlistapi.mapper.ContactMapper;
import com.todotic.contactlistapi.repository.ContactRepository;
import com.todotic.contactlistapi.repository.EnderecoRepository;

import jakarta.transaction.Transactional;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
	public List<ContactDTO> findAll() {
	    // 1. Cria um Sort tipado para a entidade Contact
	    TypedSort<Contact> sort = Sort.sort(Contact.class);
	    
	    return this.contactRepository.findAll(sort.by(Contact::getName))
	               .stream().map(this.contactMapper::toDTO)
	               .collect(Collectors.toList());
	}

	@Transactional
	public ContactDTO findById(Integer id) {
		return this.contactRepository.findById(id)
				   .map(this.contactMapper::toDTO)
				   .orElseThrow(()-> new RecordNotFoundException(id));
	}
	
	@Transactional
	public ContactDTO create(ContactDTO contactDTO) {
		Contact contactEntity = this.contactMapper.toEntity(contactDTO);
		Endereco enderecoEntity = contactEntity.getEndereco();
		
		// 2. Busca na base usando os dados da entidade
        Endereco enderecoGerenciado = enderecoRepository
                .findByCepAndComplemento(enderecoEntity.getCep(), enderecoEntity.getComplemento())
                .orElseGet(() -> {
                	// salva o endereço novo no banco caso nao encontre o endereço.
                    return enderecoRepository.save(enderecoEntity);
                });

        contactEntity.setEndereco(enderecoGerenciado);

        Contact savedContact = contactRepository.save(contactEntity);

        return contactMapper.toDTO(savedContact);
	}

	@Transactional
	public ContactDTO update(ContactDTO contactDTO, Integer id) {
	    return this.contactRepository.findById(id).map(contactUpdated -> {
	        
	        // 1. Converte o DTO para entidade temporária para extrair os dados
	        Contact contactEntity = this.contactMapper.toEntity(contactDTO);
	        
	        // 2. Atualiza os dados básicos
	        contactUpdated.setName(contactEntity.getName());
	        contactUpdated.setEmail(contactEntity.getEmail().toLowerCase());
	        
	        // 3. Trata o endereço com o orElseGet
	        if (contactEntity.getEndereco() != null) {
	            Endereco enderecoGerenciado = enderecoRepository
	                .findByCepAndComplemento(
	                    contactEntity.getEndereco().getCep(), 
	                    contactEntity.getEndereco().getComplemento()
	                )
	                .orElseGet(() -> enderecoRepository.save(contactEntity.getEndereco()));
	            
	            contactUpdated.setEndereco(enderecoGerenciado);
	        }
	        
	        // 4. Salva a entidade e JÁ converte para DTO no retorno, sem frescura de outro map
	        return this.contactMapper.toDTO(contactRepository.save(contactUpdated));
	        
	    }).orElseThrow(() -> new RecordNotFoundException(id));
	}

	@Transactional
	public void delete(Integer id) {
	    Contact contact = contactRepository.findById(id)
	            .orElseThrow(() -> new RecordNotFoundException(id));
	    contactRepository.delete(contact);
	}

}
