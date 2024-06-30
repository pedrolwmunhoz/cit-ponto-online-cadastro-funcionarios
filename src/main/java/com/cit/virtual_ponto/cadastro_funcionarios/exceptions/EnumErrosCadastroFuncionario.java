package com.cit.virtual_ponto.cadastro_funcionarios.exceptions;

public enum EnumErrosCadastroFuncionario {
    FUNCIONARIO_NAO_ENCONTRADO_ID("Funcionário com o ID não encontrado"),
    EMPRESA_NAO_ENCONTRADA("Empresa com o ID não encontrada"),

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
