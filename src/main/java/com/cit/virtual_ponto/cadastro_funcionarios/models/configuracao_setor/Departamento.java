package com.cit.virtual_ponto.cadastro_funcionarios.models.configuracao_setor;

import jakarta.persistence.*;
import lombok.Data;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaJuridica;

@Data
@Entity
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Integer idDepartamento;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false, unique = true)
    private PessoaJuridica empresa;

}

