package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public class ListarFuncionariosService {

    private CadastroFuncionariosRepository cadastroFuncionariosRepository;

    
    public List<FuncionarioEntity> listarFuncionarios() {
        try {
            return cadastroFuncionariosRepository.findAll();
        } catch (DataAccessException e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_LISTAR_FUNCIONARIOS.getMensagemErro(), e);
        }
    }

    public Optional<FuncionarioEntity> buscarFuncionarioPorId(Long id) {
        try {
            Optional<FuncionarioEntity> funcionario = cadastroFuncionariosRepository.findById(id);
            if (funcionario.isPresent()) {
                return funcionario;
            } else {
                throw new ErrosSistema.FuncionarioNotFoundException(
                        EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro() + id);
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_BUSCAR_FUNCIONARIO.getMensagemErro(), e);
        }
    }

    public List<FuncionarioEntity> buscarFuncionariosPorNome(String nome) {
        try {
            return cadastroFuncionariosRepository.findByNomeContainingIgnoreCase(nome);
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_BUSCAR_FUNCIONARIO.getMensagemErro(), e);
        }
    }
}
