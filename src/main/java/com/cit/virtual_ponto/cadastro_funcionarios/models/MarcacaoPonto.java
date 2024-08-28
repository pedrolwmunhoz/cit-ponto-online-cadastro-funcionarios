package com.cit.virtual_ponto.cadastro_funcionarios.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "marcacao_ponto")
public class MarcacaoPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marcacao_ponto_id")
    private Integer marcacao_ponto_id;

    @Column(name = "geolocalizacaoPermitida", nullable = false)
    private String geolocalizacaoPermitida;

    @Column(name = "jornada_trabalho_horas")
    private Time jornada_trabalho_horas;

    @Column(name = "horario_entrada")
    private Time horario_entrada;

    @Column(name = "horario_saida")
    private Time horario_saida;

    @Column(name = "intervalo_descanso")
    private Time intervalo_descanso;
}
