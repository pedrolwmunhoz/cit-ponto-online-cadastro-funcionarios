package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import lombok.Data;
import jakarta.persistence.*;

import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.BancoHorasEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.FolhaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "hash_nome", nullable = false, unique = true)
    private String hashNome;

    @Column(name = "hash_cpf", nullable = false, unique = true)
    private String hashCpf;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "hash_rg", nullable = false, unique = true)
    private String hashRg;

    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_folha_pagamento", nullable = false, unique = true )
    private FolhaPagamento folhaPagamento;

    @Column(name = "id_departamento", nullable = false, unique = true)
    private Integer idDepartamento;

    @Column(name = "id_cargo", nullable = false, unique = true )
    private Integer idCargo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_banco_horas", nullable = false, unique = true)
    private BancoHorasEntity bancoHoras;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_empresa")
    private PessoaJuridica empresa;
}
