package com.cit.virtual_ponto.cadastro_funcionarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    
    Optional<Login> findByHashEmail(String hashEmail);
}
