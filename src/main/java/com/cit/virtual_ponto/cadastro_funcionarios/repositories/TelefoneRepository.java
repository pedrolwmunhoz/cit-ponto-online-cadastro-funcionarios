package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Integer>{


    Optional<Telefone> findByDddAndNumero(String ddd, String numero);
}
