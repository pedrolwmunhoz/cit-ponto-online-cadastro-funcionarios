package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaFisica;
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
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }
    
    @Autowired
    public ListarFuncionariosService(CadastroFuncionariosRepository cadastroFuncionariosRepository) {
        this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
    }

    public List<PessoaFisica> listarFuncionarios() {
        List<PessoaFisica> funcionarios = cadastroFuncionariosRepository.findAll();
        funcionarios.forEach(this::decryptFuncionarioFields);
        return funcionarios;
    }

    public PessoaFisica buscarFuncionarioPorId(Long id) {
        Optional<PessoaFisica> funcionario = cadastroFuncionariosRepository.findById(id);
        if (funcionario.isPresent()) {
            PessoaFisica funcionarioExistente = funcionario.get();
            this.decryptFuncionarioFields(funcionarioExistente);
            return funcionarioExistente;
        } else {
            throw new ErrosSistema.FuncionarioException(
                    EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }

    public List<PessoaFisica> buscarFuncionariosPorNome(String nome) {

        List<PessoaFisica> funcionarios = cadastroFuncionariosRepository.findAll();
        funcionarios.forEach(this::decryptFuncionarioFields);

        List<PessoaFisica> funcionariosFiltrados = funcionarios.stream()
        .filter(funcionario -> nome.equalsIgnoreCase(funcionario.getNome()))
        .collect(Collectors.toList());
        return funcionariosFiltrados;
    }

    private void decryptFuncionarioFields(PessoaFisica funcionario) {
        funcionario.setCpf(decrypt(funcionario.getCpf()));
        funcionario.setEmail(decrypt(funcionario.getEmail()));
        funcionario.setNome(decrypt(funcionario.getNome()));
        funcionario.setSenha(decrypt(funcionario.getSenha()));
        funcionario.setDataNascimento(decrypt(funcionario.getDataNascimento()));
        funcionario.setSalario(funcionario.getSalario());
        funcionario.setCargo(decrypt(funcionario.getCargo()));
    }

    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
