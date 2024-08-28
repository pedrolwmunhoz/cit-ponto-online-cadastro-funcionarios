package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.Endereco;
import com.cit.virtual_ponto.cadastro_funcionarios.models.Login;
import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_funcionarios.models.Telefone;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.EmpresaRepository;

import jakarta.transaction.Transactional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CadastroFuncionariosService {

    private CadastroFuncionariosRepository cadastroFuncionariosRepository;
    private EmpresaRepository empresaRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public CadastroFuncionariosService(CadastroFuncionariosRepository cadastroFuncionariosRepository,
            EmpresaRepository empresaRepository) {
        this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public PessoaFisica cadastrarFuncionario(FuncionarioDto funcionario) {
        
        Long empresaId = funcionario.getEmpresaId();
        Optional<PessoaJuridica> optionalEmpresa = empresaRepository.findById(empresaId);

        // valida se empresa e funcionario existem
        this.validarCadastroFuncionario(funcionario, optionalEmpresa);

        PessoaFisica funcionarioNovo = new PessoaFisica();

        funcionarioNovo.setEmpresa(optionalEmpresa.get());
        //criptografia e settDados
        this.setFuncionarioFields(funcionarioNovo, funcionario);

        // salva funcionario
        cadastroFuncionariosRepository.save(funcionarioNovo);
        return funcionarioNovo;

    }

    @Transactional
    public PessoaFisica atualizarFuncionario(FuncionarioDto funcionario) {

        // valida se empresa existe
        Long empresaId = funcionario.getEmpresaId();
        Optional<PessoaJuridica> optionalEmpresa = empresaRepository.findById(empresaId);
        if (!optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro());
        }

        // valida se funcionario existe
        Long funcionarioId = funcionario.getPessoaId();
        Optional<PessoaFisica> optionalFuncionario = cadastroFuncionariosRepository.findById(funcionarioId);
        if (optionalFuncionario.isPresent()) {

            // criptografia dos dados
            PessoaFisica funcionarioExistente = optionalFuncionario.get();
            funcionarioExistente.setEmpresa(optionalEmpresa.get());
            this.setFuncionarioFields(funcionarioExistente, funcionario);

            // salva funcionario
            cadastroFuncionariosRepository.save(funcionarioExistente);

            return funcionarioExistente;

        } else {
            throw new ErrosSistema.FuncionarioException(
                    EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }

    @Transactional
    public boolean excluirFuncionario(Long id) {
        if (cadastroFuncionariosRepository.existsById(id)) {
            cadastroFuncionariosRepository.deleteById(id);
            return true;
        } else {
            throw new ErrosSistema.FuncionarioException(
                    EnumErrosCadastroFuncionario.FUNCIONARIO_NAO_ENCONTRADO_ID.getMensagemErro());
        }
    }

    public void validarCadastroFuncionario(FuncionarioDto funcionario, Optional<PessoaJuridica> optionalEmpresa) {

        if (!optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro() + funcionario.getEmpresaId());
        }

        // Verifica se o email já está cadastrado
        String email = funcionario.getEmail();
        Optional<PessoaFisica> optionalFuncionarioByEmail = cadastroFuncionariosRepository.findByEmail(email);
        if (optionalFuncionarioByEmail.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Email já cadastrado");
        }

        // Verifica se o nome já está cadastrado
        String nome = funcionario.getNome();
        Optional<PessoaFisica> optionalFuncionarioByNome = cadastroFuncionariosRepository.findByNome(nome);
        if (optionalFuncionarioByNome.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Nome já cadastrado");
        }

        // Verifica se o CPF já está cadastrado
        String cpf = funcionario.getCpf();
        Optional<PessoaFisica> optionalFuncionarioByCpf = cadastroFuncionariosRepository.findByCpf(cpf);
        if (optionalFuncionarioByCpf.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "CPF já cadastrado");
        }

        // Verifica se o telefone já está cadastrado
        String telefone = funcionario.getTelefone();
        Optional<PessoaFisica> optionalFuncionarioByTelefone = cadastroFuncionariosRepository
                .findByTelefone(telefone);
        if (optionalFuncionarioByTelefone.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Telefone já cadastrado");
        }
    }

    private void setFuncionarioFields(PessoaFisica novoFuncionario, FuncionarioDto funcionario) {

        // Campos criptografados
        novoFuncionario.setCpf(encryptor.encrypt(funcionario.getCpf()));
        novoFuncionario.setNome(encryptor.encrypt(funcionario.getNome()));
        novoFuncionario.setDataNascimento(encryptor.encrypt(funcionario.getDataNascimento()));

        Telefone telefone = new Telefone();
        telefone.setDdd(encryptor.encrypt(funcionario.get()));
        telefone.setNumero(encryptor.encrypt(funcionario.getTelefone()));


        Login login = new Login();
        login.setSenha(encryptor.encrypt(funcionario.getSenha()));
        login.setEmail(encryptor.encrypt(funcionario.getSenha()));
        novoFuncionario.setCargo(encryptor.encrypt(funcionario.getCargo()));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(encryptor.encrypt(funcionario.getEndereco().getLogradouro()));
        endereco.setNumero(encryptor.encrypt(funcionario.getEndereco().getNumero()));
        endereco.setComplemento(encryptor.encrypt(funcionario.getEndereco().getComplemento()));
        endereco.setBairro(encryptor.encrypt(funcionario.getEndereco().getBairro()));
        endereco.setCidade(encryptor.encrypt(funcionario.getEndereco().getCidade()));
        endereco.setEstado(encryptor.encrypt(funcionario.getEndereco().getEstado()));
        endereco.setCep(encryptor.encrypt(funcionario.getEndereco().getCep()));
        novoFuncionario.setEndereco(endereco);
        
        // Campos não criptografados
        novoFuncionario.setSalario(funcionario.getSalario());
        novoFuncionario.setDataAdmissao(funcionario.getDataAdmissao());
        novoFuncionario.setJornadaTrabalho(funcionario.getJornadaTrabalho());
        novoFuncionario.setHorarioEntrada(funcionario.getHorarioEntrada());
        novoFuncionario.setHorarioSaida(funcionario.getHorarioSaida());
        novoFuncionario.setTipoContrato(funcionario.getTipoContrato());
        novoFuncionario.setIntervaloDescanso(funcionario.getIntervaloDescanso());
        novoFuncionario.setMatricula(funcionario.getMatricula());
        novoFuncionario.setSituacao(funcionario.getSituacao());
    }
    

    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
