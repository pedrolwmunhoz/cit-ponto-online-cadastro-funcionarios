package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer idPessoa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_login", nullable = false, unique = true)
    private Login login;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false, unique = true)
    private Endereco endereco;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_telefone", nullable = false, unique = true)
    private Telefone telefone;
}
