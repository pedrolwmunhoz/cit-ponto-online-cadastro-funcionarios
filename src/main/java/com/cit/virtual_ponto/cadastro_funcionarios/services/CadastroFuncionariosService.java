package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.EmpresaEntity;
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
    public FuncionarioEntity cadastrarFuncionario(FuncionarioDto funcionario) {

        Long empresaId = funcionario.getEmpresaId();
        Optional<EmpresaEntity> optionalEmpresa = empresaRepository.findById(empresaId);

        // valida se empresa e funcionario existem
        this.validarCadastroFuncionario(funcionario, optionalEmpresa);

        FuncionarioEntity funcionarioNovo = new FuncionarioEntity();

        // criptografia dos dados
        funcionarioNovo.setEmpresa(optionalEmpresa.get());
        this.encryptFuncionarioFields(funcionarioNovo, funcionario);

        // salva funcionario
        cadastroFuncionariosRepository.save(funcionarioNovo);

        // descriptografia pra retornar funcionario
        this.decryptFuncionarioFields(funcionarioNovo);
        return funcionarioNovo;

    }

    @Transactional
    public FuncionarioEntity atualizarFuncionario(FuncionarioDto funcionario) {

        // valida se empresa existe
        Long empresaId = funcionario.getEmpresaId();
        Optional<EmpresaEntity> optionalEmpresa = empresaRepository.findById(empresaId);
        if (!optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro());
        }

        // valida se funcionario existe
        Long funcionarioId = funcionario.getFuncionarioId();
        Optional<FuncionarioEntity> optionalFuncionario = cadastroFuncionariosRepository.findById(funcionarioId);
        if (optionalFuncionario.isPresent()) {

            // criptografia dos dados
            FuncionarioEntity funcionarioExistente = optionalFuncionario.get();
            funcionarioExistente.setEmpresa(optionalEmpresa.get());
            this.encryptFuncionarioFields(funcionarioExistente, funcionario);

            // salva funcionario
            cadastroFuncionariosRepository.save(funcionarioExistente);

            // descriptografia pra retornar funcionario
            this.decryptFuncionarioFields(funcionarioExistente);
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

    public void validarCadastroFuncionario(FuncionarioDto funcionario, Optional<EmpresaEntity> optionalEmpresa) {

        if (!optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro() + funcionario.getEmpresaId());
        }

        // Verifica se o email já está cadastrado
        String email = funcionario.getEmail();
        Optional<FuncionarioEntity> optionalFuncionarioByEmail = cadastroFuncionariosRepository.findByEmail(email);
        if (optionalFuncionarioByEmail.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Email já cadastrado");
        }

        // Verifica se o nome já está cadastrado
        String nome = funcionario.getNome();
        Optional<FuncionarioEntity> optionalFuncionarioByNome = cadastroFuncionariosRepository.findByNome(nome);
        if (optionalFuncionarioByNome.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Nome já cadastrado");
        }

        // Verifica se o CPF já está cadastrado
        String cpf = funcionario.getCpf();
        Optional<FuncionarioEntity> optionalFuncionarioByCpf = cadastroFuncionariosRepository.findByCpf(cpf);
        if (optionalFuncionarioByCpf.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "CPF já cadastrado");
        }

        // Verifica se o telefone já está cadastrado
        String telefone = funcionario.getTelefone();
        Optional<FuncionarioEntity> optionalFuncionarioByTelefone = cadastroFuncionariosRepository
                .findByTelefone(telefone);
        if (optionalFuncionarioByTelefone.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Telefone já cadastrado");
        }

    }

    private void encryptFuncionarioFields(FuncionarioEntity novoFuncionario, FuncionarioDto funcionario) {
        novoFuncionario.setCpf(encrypt(funcionario.getCpf()));
        novoFuncionario.setEmail(encrypt(funcionario.getEmail()));
        novoFuncionario.setNome(encrypt(funcionario.getNome()));
        novoFuncionario.setTelefone(encrypt(funcionario.getTelefone()));
    }

    public String encrypt(String encryptedValue) {
        return encryptor.encrypt(encryptedValue);
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
