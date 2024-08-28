package com.cit.virtual_ponto.cadastro_funcionarios.models;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @ManyToOne
    @JoinColumn(name = "folha_pagamento_id", nullable = false)
    private FolhaPagamento folha_pagamento;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private PessoaJuridica empresa_id;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;
    
    @ManyToOne
    @JoinColumn(name = "banco_horas_id", nullable = false)
    private BancoHoras bancoHoras;

    @OneToMany(mappedBy = "pessoaFisica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarcacaoPonto> marcacoesPonto;
}
