package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListarFuncionariosService {

    private CadastroFuncionariosRepository cadastroFuncionariosRepository;

    private StringEncryptor encryptor;

    @Autowired
    public ListarFuncionariosService(CadastroFuncionariosRepository cadastroFuncionariosRepository) {
        this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
    }

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public List<FuncionarioEntity> listarFuncionarios() {
        List<FuncionarioEntity> funcionarios = cadastroFuncionariosRepository.findAll();
        funcionarios.forEach(this::decryptFuncionarioFields);
        return funcionarios;
    }

    public FuncionarioEntity buscarFuncionarioPorId(Long id) {
        Optional<FuncionarioEntity> funcionario = cadastroFuncionariosRepository.findById(id);
        if (funcionario.isPresent()) {
            FuncionarioEntity funcionarioExistente = funcionario.get();
            this.decryptFuncionarioFields(funcionarioExistente);
            return funcionarioExistente;
        } else {
            throw new ErrosSistema.FuncionarioException(
                    EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro() + id);
        }
    }

    public List<FuncionarioEntity> buscarFuncionariosPorNome(String nome) {

        List<FuncionarioEntity> funcionarios = cadastroFuncionariosRepository.findAll();
        funcionarios.forEach(this::decryptFuncionarioFields);

        List<FuncionarioEntity> funcionariosFiltrados = funcionarios.stream()
        .filter(funcionario -> nome.equalsIgnoreCase(funcionario.getNome()))
        .collect(Collectors.toList());
        return funcionariosFiltrados;
    }

    private void decryptFuncionarioFields(FuncionarioEntity funcionario) {
        funcionario.setCpf(decrypt(funcionario.getCpf()));
        funcionario.setEmail(decrypt(funcionario.getEmail()));
        funcionario.setNome(decrypt(funcionario.getNome()));
        funcionario.setTelefone(decrypt(funcionario.getTelefone()));
    }

    public String decrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
    }
}
