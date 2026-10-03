package com.todotic.contactlistapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP em formato inválido. Use 00000-000 ou 00000000")
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    // Opcional: muitos endereços não possuem complemento
    @Column(name = "complemento")
    private String complemento;

    // Opcional
    @Column(name = "unidade")
    private String unidade;

    @NotBlank(message = "O bairro é obrigatório")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "A localidade/cidade é obrigatória")
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @NotBlank(message = "A UF é obrigatória")
    @Size(min = 2, max = 2, message = "A UF deve conter exatamente 2 caracteres")
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "O estado é obrigatório")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotBlank(message = "A região é obrigatória")
    @Column(name = "regiao", nullable = false)
    private String regiao;

    // Campos opcionais retornado pelas APIs de CEP
    @Column(name = "ibge")
    private String ibge;

    @Column(name = "gia")
    private String gia;

    @NotBlank(message = "O DDD é obrigatório")
    @Size(min = 2, max = 2, message = "O DDD deve ter 2 dígitos")
    @Column(name = "ddd", nullable = false, length = 2)
    private String ddd;

    @Column(name = "siafi")
    private String siafi;
}