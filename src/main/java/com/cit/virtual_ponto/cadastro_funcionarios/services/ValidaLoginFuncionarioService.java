package com.cit.virtual_ponto.cadastro_funcionarios.services;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;

@Service
public class ValidaLoginFuncionarioService {
    
    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }
    @Autowired
    private CadastroFuncionariosRepository cadastroFuncionariosRepository;

    public PessoaFisica validarLogin(LoginRequestDto loginRequestDto) {
        List<PessoaFisica> funcionarios = cadastroFuncionariosRepository.findAll();

        Optional<PessoaFisica> funcionarioOptional = funcionarios.stream()
                .filter(funcionario -> encryptor.decrypt(funcionario.getEmail()).equals(loginRequestDto.getEmail()))
                .findFirst();

        if (funcionarioOptional.isPresent()) {
            PessoaFisica funcionario = funcionarioOptional.get();

            String senhaDescriptografada = encryptor.decrypt(funcionario.getSenha());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                this.decryptFuncionarioFields(funcionario);
                return funcionario; 
            }
        }
        throw new ErrosSistema.FuncionarioException("Credenciais inválidas.");
    }


    private void decryptFuncionarioFields(PessoaFisica funcionario) {
        funcionario.setCpf(encryptor.decrypt(funcionario.getCpf()));
        funcionario.setEmail(encryptor.decrypt(funcionario.getEmail()));
        funcionario.setNome(encryptor.decrypt(funcionario.getNome()));
        funcionario.setSenha(encryptor.decrypt(funcionario.getSenha()));
        funcionario.setDataNascimento(encryptor.decrypt(funcionario.getDataNascimento()));
        funcionario.setSalario(funcionario.getSalario());
        funcionario.setCargo(encryptor.decrypt(funcionario.getCargo()));
    }

}

