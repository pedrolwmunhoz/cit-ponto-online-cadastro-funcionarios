package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.cit.virtual_ponto.cadastro_funcionarios.models.configuracao_setor.Cargo;
import com.cit.virtual_ponto.cadastro_funcionarios.models.configuracao_setor.Departamento;

@Data
@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica extends Pessoa {

    @Column(name = "nomeFantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "nomeFantasia", nullable = false)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "inscricao_estadual", nullable = false, unique = true)
    private String inscricao_estadual;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cargo> cargo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamento;
}
