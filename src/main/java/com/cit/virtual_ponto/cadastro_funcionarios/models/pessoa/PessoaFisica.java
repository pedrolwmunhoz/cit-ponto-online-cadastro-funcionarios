package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import lombok.Data;
import jakarta.persistence.*;

import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.BancoHorasEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.FolhaPagamento;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.JornadaTrabalhoEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.configuracao_setor.Cargo;
import com.cit.virtual_ponto.cadastro_funcionarios.models.configuracao_setor.Departamento;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_folha_pagamento", unique = true )
    private FolhaPagamento folhaPagamento;

    @Column(name = "id_departamento", nullable = false, unique = true)
    private Integer idDepartamento;

    @Column(name = "id_cargo", nullable = false, unique = true )
    private Integer idCargo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_banco_horas", unique = true)
    private BancoHorasEntity bancoHoras;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private PessoaJuridica empresa;
}
