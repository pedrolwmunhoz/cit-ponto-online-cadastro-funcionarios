package com.cit.virtual_ponto.cadastro_funcionarios.services;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
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

    public FuncionarioEntity validarLogin(LoginRequestDto loginRequestDto) {
        List<FuncionarioEntity> funcionarios = cadastroFuncionariosRepository.findAll();

        Optional<FuncionarioEntity> funcionarioOptional = funcionarios.stream()
                .filter(funcionario -> encryptor.decrypt(funcionario.getEmail()).equals(loginRequestDto.getEmail()))
                .findFirst();

        if (funcionarioOptional.isPresent()) {
            FuncionarioEntity funcionario = funcionarioOptional.get();

            String senhaDescriptografada = encryptor.decrypt(funcionario.getSenha());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                return funcionario; 
            }
        }
        throw new ErrosSistema.FuncionarioException("Credenciais inválidas.");
    }
}

