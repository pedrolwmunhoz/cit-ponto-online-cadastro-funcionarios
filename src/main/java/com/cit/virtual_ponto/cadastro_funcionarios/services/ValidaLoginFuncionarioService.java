package com.cit.virtual_ponto.cadastro_funcionarios.services;

import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Login;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.LoginRepository;

@Service
public class ValidaLoginFuncionarioService {
    
    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private CadastroFuncionariosRepository cadastroFuncionariosRepository;

    @Autowired
    private HashService hashService;

    public PessoaFisica validarLogin(LoginRequestDto loginRequestDto) {
        Optional<Login> loginOptional = loginRepository.findByHashEmail(hashService.generateHash(loginRequestDto.getEmail()));

        if (loginOptional.isPresent()) {
            Login login = loginOptional.get();

            String senhaDescriptografada = encryptor.decrypt(login.getSenhaUsuario());

            if (senhaDescriptografada.equals(loginRequestDto.getSenha())) {
                PessoaFisica pessoa = cadastroFuncionariosRepository.getReferenceById(login.getIdLogin());
                this.decryptFuncionarioFields(pessoa);
                return pessoa;
            }
        }
        throw new ErrosSistema.FuncionarioException("Credenciais inválidas.");
    }

    private void decryptFuncionarioFields(PessoaFisica pessoa) {
        
        //Nome - RG cpf - dataNascimento - email
        pessoa.setNome(encryptor.decrypt(pessoa.getNome()));
        pessoa.setRg(encryptor.decrypt(pessoa.getRg()));
        pessoa.setCpf(encryptor.decrypt(pessoa.getCpf()));
        pessoa.setDataNascimento(encryptor.decrypt(pessoa.getDataNascimento()));
        //Telefone
        pessoa.getTelefone().setDdd(encryptor.decrypt(pessoa.getTelefone().getDdd()));
        pessoa.getTelefone().setNumero(encryptor.decrypt(pessoa.getTelefone().getNumero()));
        
        //Login
        pessoa.getLogin().setSenhaUsuario(encryptor.decrypt( pessoa.getLogin().getSenhaUsuario()));
        pessoa.getLogin().setEmail(encryptor.decrypt( pessoa.getLogin().getEmail()));
    
        //endereco
        pessoa.getEndereco().setLogradouro(encryptor.decrypt(pessoa.getEndereco().getLogradouro()));
        pessoa.getEndereco().setNumero(encryptor.decrypt(pessoa.getEndereco().getNumero()));
        pessoa.getEndereco().setComplemento(encryptor.decrypt(pessoa.getEndereco().getComplemento()));
        pessoa.getEndereco().setBairro(encryptor.decrypt(pessoa.getEndereco().getBairro()));
        pessoa.getEndereco().setCidade(encryptor.decrypt(pessoa.getEndereco().getCidade()));
        pessoa.getEndereco().setEstado(encryptor.decrypt(pessoa.getEndereco().getEstado()));
        pessoa.getEndereco().setCep(encryptor.decrypt(pessoa.getEndereco().getCep()));
    }

}

