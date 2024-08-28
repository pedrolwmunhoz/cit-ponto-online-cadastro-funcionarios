package com.cit.virtual_ponto.cadastro_funcionarios.models;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "banco_horas")
public class BancoHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banco_horas_id")
    private Integer banco_horas_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private PessoaFisica funcionario;

    @Column(name = "data_inicio_apuracao", nullable = false)
    private Date data_inicio_apuracao;

    @Column(name = "saldo_horas_positivo", precision = 5, scale = 2)
    private BigDecimal saldo_horas_positivo;

    @Column(name = "saldo_horas_negativo", precision = 5, scale = 2)
    private BigDecimal saldo_horas_negativo;

    @Column(name = "horas_compensadas", precision = 5, scale = 2)
    private BigDecimal horas_compensadas;
}
