package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;

public interface CadastroFuncionariosRepository extends JpaRepository<PessoaFisica, Integer> {
   
    Optional<PessoaFisica> findByNome(String nome);

    Optional<PessoaFisica> findByCpf(String cpf);

    Optional<PessoaFisica> findByRg(String rg);

    Optional<PessoaFisica> findByEmail(String email);


}
