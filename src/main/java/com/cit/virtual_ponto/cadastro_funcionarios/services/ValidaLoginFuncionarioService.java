package com.cit.virtual_ponto.cadastro_funcionarios.services;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
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

            String senhaDescriptografada = encryptor.decrypt(funcionario.getLogin().getSenhaUsuario());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                this.decryptFuncionarioFields(funcionario);
                return funcionario; 
            }
        }
        throw new ErrosSistema.FuncionarioException("Credenciais inválidas.");
    }

    private void decryptFuncionarioFields(PessoaFisica funcionario) {
        
        //Nome - RG cpf - dataNascimento - email
        funcionario.setNome(encryptor.decrypt(funcionario.getNome()));
        funcionario.setRg(encryptor.decrypt(funcionario.getRg()));
        funcionario.setCpf(encryptor.decrypt(funcionario.getCpf()));
        funcionario.setDataNascimento(encryptor.decrypt(funcionario.getDataNascimento()));
        funcionario.setEmail(encryptor.decrypt(funcionario.getEmail()));

        //Telefone
        funcionario.getTelefone().setDdd(encryptor.decrypt(funcionario.getTelefone().getDdd()));
        funcionario.getTelefone().setNumero(encryptor.decrypt(funcionario.getTelefone().getNumero()));
        
        //Login
        funcionario.getLogin().setSenhaUsuario(encryptor.decrypt(funcionario.getLogin().getSenhaUsuario()));
        funcionario.getLogin().setEmail(encryptor.decrypt(funcionario.getLogin().getEmail()));
    
        //endereco
        funcionario.getEndereco().setLogradouro(encryptor.decrypt(funcionario.getEndereco().getLogradouro()));
        funcionario.getEndereco().setNumero(encryptor.decrypt(funcionario.getEndereco().getNumero()));
        funcionario.getEndereco().setComplemento(encryptor.decrypt(funcionario.getEndereco().getComplemento()));
        funcionario.getEndereco().setBairro(encryptor.decrypt(funcionario.getEndereco().getBairro()));
        funcionario.getEndereco().setCidade(encryptor.decrypt(funcionario.getEndereco().getCidade()));
        funcionario.getEndereco().setEstado(encryptor.decrypt(funcionario.getEndereco().getEstado()));
        funcionario.getEndereco().setCep(encryptor.decrypt(funcionario.getEndereco().getCep()));
    }

}

