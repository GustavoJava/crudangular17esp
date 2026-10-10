package com.todotic.contactlistapi.mapper;

import org.springframework.stereotype.Component;

import com.todotic.contactlistapi.dto.EnderecoDTO;
import com.todotic.contactlistapi.entity.Endereco;

@Component
public class EnderecoMapper {

    public EnderecoDTO toDTO(Endereco endereco) {
        
    	if (endereco == null) {
            return null;
        }

        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .complemento(endereco.getComplemento())
                .unidade(endereco.getUnidade())
                .bairro(endereco.getBairro())
                .localidade(endereco.getLocalidade())
                .uf(endereco.getUf())
                .estado(endereco.getEstado())
                .regiao(endereco.getRegiao())
                .ibge(endereco.getIbge())
                .gia(endereco.getGia())
                .ddd(endereco.getDdd())
                .siafi(endereco.getSiafi())
                .build();
    }

    public Endereco toEntity(EnderecoDTO dto) {
        
    	if (dto == null) {
            return null;
        }

        return Endereco.builder()
                .id(dto.getId())
                .cep(dto.getCep())
                .logradouro(dto.getLogradouro())
                .complemento(dto.getComplemento())
                .unidade(dto.getUnidade())
                .bairro(dto.getBairro())
                .localidade(dto.getLocalidade())
                .uf(dto.getUf())
                .estado(dto.getEstado())
                .regiao(dto.getRegiao())
                .ibge(dto.getIbge())
                .gia(dto.getGia())
                .ddd(dto.getDdd())
                .siafi(dto.getSiafi())
                .build();
    }
    
}
