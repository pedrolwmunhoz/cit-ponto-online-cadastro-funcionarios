package com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "saldo_horas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoHorasEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_saldo_horas")
    private Integer idSaldoHoras;

    @Column(name = "saldo_horas_trabalhadas")
    private String saldoHorasTrabalhadas;

    @Column(name = "saldo_horas_extras")
    private String saldoHorasExtras;

    @JsonIgnore
    @OneToMany(mappedBy = "saldoHoras")
    private List<BancoHorasEntity> bancoHoras;
    
}
