package com.cit.virtual_ponto.cadastro_funcionarios.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Data
@Table(name = "funcionario")
@Entity(name = "FuncionarioEntity")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long funcionarioId;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;

    @Column(unique = true)
    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefone;

    @Column(nullable = false)
    private String senha;

    @Column()
    private String data_nascimento;

    @Column()
    private String salario;

    @Column()
    private String cargo;

}
