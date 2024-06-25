package com.cit.virtual_ponto.cadastro_funcionarios.exceptions;

public enum EnumErrosCadastroFuncionario {
    FUNCIONARIO_NAO_ENCONTRADO_ID("Funcionário não encontrado com o ID:"),
    EMPRESA_NAO_ENCONTRADA("Empresa não encontrada com o ID: "),

    ERRO_CADASTRAR_FUNCIONARIO("Erro ao cadastrar funcionário"),
    ERRO_ATUALIZAR_FUNCIONARIO("Erro ao atualizar funcionário"),
    ERRO_EXCLUIR_FUNCIONARIO("Erro ao excluir funcionário"),
    ERRO_BUSCAR_FUNCIONARIO("Erro ao buscar funcionário"),
    ERRO_LISTAR_FUNCIONARIOS("Erro ao listar os funcionários");

    private final String mensagemErro;

    EnumErrosCadastroFuncionario(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
