package com.cit.virtual_ponto.cadastro_funcionarios.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
public class JornadaTrabalhoDto implements Serializable {

    private Integer idConfiguracaoJornadaTrabalho;

    @NotBlank(message = "Geolocalização permitida não pode ser vazia")
    private String geolocalizacaoPermitida;

    @NotNull(message = "Jornada de trabalho horas não pode ser nula")
    private LocalTime jornadaTrabalhoHoras;

    @NotNull(message = "Horário de entrada não pode ser nulo")
    private LocalTime horarioEntrada;

    @NotNull(message = "Horário de saída não pode ser nulo")
    private LocalTime horarioSaida;

    @NotNull(message = "Intervalo de descanso não pode ser nulo")
    private LocalTime intervaloDescanso;
}