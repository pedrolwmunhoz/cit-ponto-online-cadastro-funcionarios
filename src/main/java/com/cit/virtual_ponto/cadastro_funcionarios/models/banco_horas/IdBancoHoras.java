package com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdBancoHoras implements Serializable {

    private Integer idBancoHoras;
    private Integer idFuncionario;
}
