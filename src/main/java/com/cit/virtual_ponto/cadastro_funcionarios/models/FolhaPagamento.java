package com.cit.virtual_ponto.cadastro_funcionarios.models;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "folha_pagamento")
public class FolhaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folha_pagamento_id")
    private Integer folha_pagamento_id;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "tipo_contrato")
    private String tipo_contrato;

    @Column(name = "data_admissao")
    private Date data_admissao;

    @Column(name = "numero_filhos")
    private String numero_filhos;

    @Column(name = "salario")
    private String salario;

    @Column(name = "situacao")
    private String situacao;
}
