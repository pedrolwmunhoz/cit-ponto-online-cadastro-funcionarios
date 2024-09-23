package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica extends Pessoa {

    @Column(name = "hash_nome_fantasia", nullable = false, unique = true)
    private String hashNomeFantaia;

    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "hash_cnpj", nullable = false, unique = true)
    private String hashCnpj;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "inscricao_estadual", nullable = false)
    private String inscricao_estadual;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;

}
