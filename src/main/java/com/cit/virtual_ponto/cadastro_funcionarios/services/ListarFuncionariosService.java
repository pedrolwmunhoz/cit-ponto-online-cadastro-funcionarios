package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
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

    public PessoaFisica buscarFuncionarioPorId(Integer id) {
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
