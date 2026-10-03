package com.todotic.contactlistapi.mapper;

import org.springframework.stereotype.Component;

import com.todotic.contactlistapi.dto.ContactDTO;
import com.todotic.contactlistapi.dto.EnderecoDTO;
import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.entity.Endereco;
import com.todotic.contactlistapi.utils.DateUtils;

@Component
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        if (contact == null) {
            return null;
        }

        // 1. Converte a Entidade Endereco para EnderecoDTO via Builder
        EnderecoDTO enderecoDTO = null;
        if (contact.getEndereco() != null) {
            Endereco e = contact.getEndereco();
            enderecoDTO = EnderecoDTO.builder()
                .id(e.getId())
                .cep(e.getCep())
                .logradouro(e.getLogradouro())
                .complemento(e.getComplemento())
                .unidade(e.getUnidade())
                .bairro(e.getBairro())
                .localidade(e.getLocalidade())
                .uf(e.getUf())
                .estado(e.getEstado())
                .regiao(e.getRegiao())
                .ibge(e.getIbge())
                .gia(e.getGia())
                .ddd(e.getDdd())
                .siafi(e.getSiafi())
                .build();
        }

        // 2. Converte a Entidade Contact para ContactDTO via Builder
        return ContactDTO.builder()
            .id(contact.getId())
            .name(contact.getName())
            .email(contact.getEmail())
            .createdAt(contact.getCreatedAt())
            .endereco(enderecoDTO)
            .build();
    }

    public Contact toEntity(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        }

        // 1. Converte o EnderecoDTO para a Entidade Endereco via Builder
        Endereco endereco = null;
        if (contactDTO.getEndereco() != null) {
            EnderecoDTO e = contactDTO.getEndereco();
            endereco = Endereco.builder()
                .id(e.getId())
                .cep(e.getCep())
                .logradouro(e.getLogradouro())
                .complemento(e.getComplemento())
                .unidade(e.getUnidade())
                .bairro(e.getBairro())
                .localidade(e.getLocalidade())
                .uf(e.getUf())
                .estado(e.getEstado())
                .regiao(e.getRegiao())
                .ibge(e.getIbge())
                .gia(e.getGia())
                .ddd(e.getDdd())
                .siafi(e.getSiafi())
                .build();
        }

        // 2. Converte o ContactDTO para a Entidade Contact via Builder
        return Contact.builder()
            .id(contactDTO.getId())
            .name(contactDTO.getName())
            .email(contactDTO.getEmail())
            .createdAt(contactDTO.getCreatedAt() != null ? contactDTO.getCreatedAt() : DateUtils.getHoje())
            .endereco(endereco)
            .build();
    }
}