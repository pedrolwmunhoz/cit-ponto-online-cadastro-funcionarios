package com.cit.virtual_ponto.cadastro_funcionarios.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Integer pessoa_id;

    @Column(name = "nome", nullable = false)
    private String nome;
    

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Login login_id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "telefone_id", nullable = false)
    private Telefone telefone_id;
}
