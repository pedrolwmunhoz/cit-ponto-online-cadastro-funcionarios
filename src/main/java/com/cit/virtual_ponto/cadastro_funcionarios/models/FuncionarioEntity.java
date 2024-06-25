package com.cit.virtual_ponto.cadastro_funcionarios.models;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Table(name = "funcionario")
@Entity(name = "FuncionarioEntity")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long funcionarioId;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;

    private String nome;

    private String cpf;

    private String email;

    private String telefone;

}
