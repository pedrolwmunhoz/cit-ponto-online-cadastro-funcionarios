package com.cit.virtual_ponto.cadastro_funcionarios.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class FuncionarioDto {

    private Long funcionarioId;

    @NotNull(message = "Empresa ID não pode ser nulo")
    private Long empresaId;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(min = 8, max = 100, message = "Senha deve conter 8 ou mais caracteres")
    private String senha;


    @NotBlank(message = "Telefone não pode ser vazio")
    @Size(min = 8, max = 100, message = "telefone deve conter 8 ou mais caracteres")
    private String telefone;


    @NotBlank(message = "data_nascimento não pode ser vazio")
    @Size(min = 2, max = 100, message = "Data ter entre 2 e 100 caracteres")
    private String data_nascimento;

    @NotBlank(message = "salario não pode ser vazio")
    @Size(min = 2, max = 100, message = "Data ter entre 2 e 100 caracteres")
    private String salario;

    @NotBlank(message = "cargo não pode ser vazio")
    @Size(min = 2, max = 100, message = "Data ter entre 2 e 100 caracteres")
    private String cargo;

}
