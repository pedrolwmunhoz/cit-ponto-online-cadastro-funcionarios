package com.cit.virtual_ponto.cadastro_funcionarios.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}
