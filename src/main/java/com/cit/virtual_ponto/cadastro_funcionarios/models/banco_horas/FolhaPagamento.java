package com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "folha_pagamento")
public class FolhaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_folha_pagamento")
    private Integer IdfolhaPagamento;

    @Column(name = "tipo_contrato")
    private String tipoContrato;

    @Column(name = "data_admissao")
    private Date dataAdmissao;

    @Column(name = "numero_filhos")
    private Integer numeroFilhos;

    @Column(name = "salario")
    private BigDecimal salario;

    @JsonIgnore
    @OneToMany(mappedBy = "folhaPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;
}
