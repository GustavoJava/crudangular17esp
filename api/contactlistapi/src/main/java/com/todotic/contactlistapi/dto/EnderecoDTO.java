package com.todotic.contactlistapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Integer id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP em formato inválido. Use 00000-000 ou 00000000")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    private String complemento;

    private String unidade;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Localidade é obrigatória")
    private String localidade;

    @NotBlank(message = "UF é obrigatória")
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    private String uf;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotBlank(message = "Região é obrigatória")
    private String regiao;

    private String ibge;

    private String gia;

    @NotBlank(message = "DDD é obrigatório")
    @Size(min = 2, max = 2, message = "DDD deve ter 2 dígitos")
    private String ddd;

    private String siafi;
}

