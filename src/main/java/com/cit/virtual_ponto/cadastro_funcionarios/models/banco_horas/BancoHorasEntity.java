package com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;

@Entity
@Table(name = "banco_horas")
@IdClass(IdBancoHoras.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BancoHorasEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco_horas")
    private Integer idBancoHoras;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false, unique = true)
    private PessoaFisica funcionario;

    @Id
    @Column(name = "id_saldo_horas", unique = true)
    private Integer idSaldoHoras;

    @ManyToOne
    @JoinColumn(name = "id_jornada_trabalho", unique = true)
    private JornadaTrabalhoEntity jornadaTrabalho;

    @Column(name = "data_inicio_apuracao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicioApuracao;

    @OneToMany(mappedBy = "bancoHoras", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaFisica> funcionarios;

}
