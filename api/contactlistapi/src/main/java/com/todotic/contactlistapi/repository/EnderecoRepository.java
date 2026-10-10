package com.todotic.contactlistapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todotic.contactlistapi.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	Optional<Endereco> findByCepAndComplemento(String cep, String complemento);

}
