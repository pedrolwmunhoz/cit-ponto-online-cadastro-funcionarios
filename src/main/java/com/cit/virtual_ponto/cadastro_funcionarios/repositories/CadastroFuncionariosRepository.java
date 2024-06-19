package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.cadastro_funcionarios.CadastroFuncionariosEntity;

public interface CadastroFuncionariosRepository extends JpaRepository<CadastroFuncionariosEntity, Long> {
}