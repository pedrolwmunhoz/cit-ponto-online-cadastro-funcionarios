package com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "jornada_trabalho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaTrabalhoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jornada_trabalho")
    private Integer idJornadaTrabalho;

    @Column(name = "geolocalizacaoPermitida")
    private String geolocalizacaoPermitida;

    @Column(name = "jornada_trabalho_horas")
    private LocalTime jornadaTrabalhoHoras;

    @Column(name = "horario_entrada")
    private LocalTime horarioEntrada;

    @Column(name = "horario_saida")
    private LocalTime horarioSaida;

    @Column(name = "intervalo_descanso")
    private LocalTime intervaloDescanso;

    @JsonIgnore
    @OneToMany(mappedBy = "jornadaTrabalho")
    private List<BancoHorasEntity> bancoHoras;
}
