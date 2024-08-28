package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaFisica;

public interface CadastroFuncionariosRepository extends JpaRepository<PessoaFisica, Long> {
    Optional<PessoaFisica> findByEmail(String email);

    Optional<PessoaFisica> findByCpf(String cpf);

    Optional<PessoaFisica> findByTelefone(String telefone);

    Optional<PessoaFisica> findByNome(String nome);
}
