package com.cit.virtual_ponto.cadastro_funcionarios.models.cadastro_funcionarios;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Table(name = "funcionario")
@Entity(name = "FuncionarioEntity")
public class CadastroFuncionariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long empresaId;

    private String nome;

    private String cpf;

    private String email;

    private String telefone;

}
