package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import lombok.Data;
import jakarta.persistence.*;

import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.BancoHorasEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.FolhaPagamento;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.JornadaTrabalhoEntity;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_folha_pagamento")
    private FolhaPagamento folhaPagamento;

    @Id
    @Column(name = "id_departamento", unique = true)
    private Integer idDepartamento;

    @Id
    @Column(name = "id_cargo", unique = true)
    private Integer idCargo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_jornada_trabalho", unique = true)
    private JornadaTrabalhoEntity jornadaTrabalho;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_banco_horas", unique = true)
    private BancoHorasEntity bancoHoras;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id", unique = true)
    private PessoaJuridica empresa;
}
