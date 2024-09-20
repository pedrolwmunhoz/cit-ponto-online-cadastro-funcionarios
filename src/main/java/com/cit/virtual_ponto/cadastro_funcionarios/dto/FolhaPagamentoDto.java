package com.cit.virtual_ponto.cadastro_funcionarios.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FolhaPagamentoDto implements Serializable {

    private Integer idFolhaPagamento;

    @NotBlank(message = "Tipo de contrato não pode ser vazio")
    private String tipoContrato;

    @NotNull(message = "Data de admissão não pode ser nula")
    private Date dataAdmissao;

    @NotNull(message = "Número de filhos não pode ser nulo")
    private Integer numeroFilhos;

    @NotNull(message = "Salário não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salário deve ser maior que zero")
    private BigDecimal salario;
}