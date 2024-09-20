package com.cit.virtual_ponto.cadastro_funcionarios.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Data
public class FuncionarioDto extends PessoaDto {

    @NotNull(message = "Funcionario ID não pode ser nulo")
    private Integer idFuncionario;


    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "RG não pode ser vazio")
    @Pattern(regexp = "\\d{9}", message = "RG deve conter 9 dígitos numéricos")
    private String rg;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private String dataNascimento;

    @NotBlank(message = "ID empresa não pode ser vazio")
    private Integer idEmpresa;

    @NotBlank(message = "ID departamento não pode ser vazio")
    private Integer idDepartamento;

    @NotBlank(message = "ID cargo não pode ser vazio")
    private Integer idCargo;

    // @NotNull(message = "ID folha pagamento não pode ser nula")
    // private Integer idFolhaPagamento;

    // @NotBlank(message = "ID jornada trabalho não pode ser vazio")
    // private Integer idJornada_trabalho;

    // @NotBlank(message = "ID banco de horas não pode ser vazio")
    // private Integer idBancoHoras;

    private @Valid JornadaTrabalhoDto jornadaTrabalho;

    private @Valid FolhaPagamentoDto folhaPagamento;
}
