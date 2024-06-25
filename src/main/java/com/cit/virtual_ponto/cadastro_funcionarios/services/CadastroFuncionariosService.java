package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.EmpresaEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CadastroFuncionariosService {

    private CadastroFuncionariosRepository cadastroFuncionariosRepository;
    private EmpresaRepository empresaRepository;

    @Autowired
    public CadastroFuncionariosService(CadastroFuncionariosRepository cadastroFuncionariosRepository,
            EmpresaRepository empresaRepository) {
        this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
        this.empresaRepository = empresaRepository;
    }

    public FuncionarioEntity cadastrarFuncionario(FuncionarioEntity funcionario) {
        try {
            Long empresaId = funcionario.getEmpresa().getEmpresaId();
            Optional<EmpresaEntity> optionalEmpresa = empresaRepository.findById(empresaId);
            if (!optionalEmpresa.isPresent()) {
                throw new ErrosSistema.EmpresaException(
                        EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro() + empresaId);
            }
            funcionario.setEmpresa(optionalEmpresa.get());
            return cadastroFuncionariosRepository.save(funcionario);
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_CADASTRAR_FUNCIONARIO.getMensagemErro(), e);
        }
    }

    public FuncionarioEntity atualizarFuncionario(FuncionarioEntity funcionario) {
        Long funcionarioId = funcionario.getFuncionarioId();
        try {
            Optional<FuncionarioEntity> optionalFuncionario = cadastroFuncionariosRepository.findById(funcionarioId);
            if (optionalFuncionario.isPresent()) {
                FuncionarioEntity funcionarioExistente = optionalFuncionario.get();
                Long empresaId = funcionario.getEmpresa().getEmpresaId();
                Optional<EmpresaEntity> optionalEmpresa = empresaRepository.findById(empresaId);
                if (!optionalEmpresa.isPresent()) {
                    throw new ErrosSistema.EmpresaException(
                            EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro() + empresaId);
                }
                funcionarioExistente.setNome(funcionario.getNome());
                funcionarioExistente.setCpf(funcionario.getCpf());
                funcionarioExistente.setEmail(funcionario.getEmail());
                funcionarioExistente.setTelefone(funcionario.getTelefone());
                funcionarioExistente.setEmpresa(optionalEmpresa.get());

                return cadastroFuncionariosRepository.save(funcionarioExistente);
            } else {
                throw new ErrosSistema.FuncionarioException(
                        EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro() + funcionarioId);
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_ATUALIZAR_FUNCIONARIO.getMensagemErro(), e);
        }
    }

    public boolean excluirFuncionario(Long id) {
        try {
            if (cadastroFuncionariosRepository.existsById(id)) {
                cadastroFuncionariosRepository.deleteById(id);
                return true;
            } else {
                throw new ErrosSistema.FuncionarioException(
                        EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro() + id);
            }
        } catch (Exception e) {
            throw new ErrosSistema.DatabaseException(
                    EnumErrosCadastroFuncionario.ERRO_EXCLUIR_FUNCIONARIO.getMensagemErro(), e);
        }
    }
}
