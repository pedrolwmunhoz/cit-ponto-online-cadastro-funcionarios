package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;

public interface CadastroFuncionariosRepository extends JpaRepository<FuncionarioEntity, Long> {
    List<FuncionarioEntity> findByNomeContainingIgnoreCase(String nome);
    
    Optional<FuncionarioEntity> findByEmail(String email);

    Optional<FuncionarioEntity> findByCpf(String cpf);

    Optional<FuncionarioEntity> findByTelefone(String telefone);

    Optional<FuncionarioEntity> findByNome(String nome);
}
