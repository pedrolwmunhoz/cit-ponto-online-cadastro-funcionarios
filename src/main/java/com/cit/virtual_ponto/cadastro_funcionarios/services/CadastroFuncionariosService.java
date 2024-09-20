package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.EnumErrosCadastroFuncionario;
import com.cit.virtual_ponto.cadastro_funcionarios.exceptions.ErrosSistema;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.BancoHorasEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.FolhaPagamento;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.JornadaTrabalhoEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.banco_horas.SaldoHorasEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Endereco;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Login;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaJuridica;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.Telefone;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.EmpresaRepository;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.TelefoneRepository;

import jakarta.transaction.Transactional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CadastroFuncionariosService {

    private CadastroFuncionariosRepository cadastroFuncionariosRepository;
    private EmpresaRepository empresaRepository;
    private TelefoneRepository telefoneRepository;

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public CadastroFuncionariosService(
        CadastroFuncionariosRepository cadastroFuncionariosRepository, 
        EmpresaRepository empresaRepository,
        TelefoneRepository telefoneRepository
    )   {
            this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
            this.empresaRepository = empresaRepository;
            this.telefoneRepository = telefoneRepository;
    }

    @Transactional
    public PessoaFisica cadastrarFuncionario(FuncionarioDto funcionario) {
        
        Integer empresaId = funcionario.getIdEmpresa();
        Optional<PessoaJuridica> optionalEmpresa = empresaRepository.findById(empresaId);

        // valida se empresa e funcionario existem
        this.validarCadastroFuncionario(funcionario, optionalEmpresa);

        PessoaFisica novoFuncionario = new PessoaFisica();

        novoFuncionario.setEmpresa(optionalEmpresa.get());
        //criptografia e settDados
        this.setFuncionarioFields(novoFuncionario, funcionario);

        // //salva funcionario
        return cadastroFuncionariosRepository.save(novoFuncionario);

    }

    @Transactional
    public PessoaFisica atualizarFuncionario(FuncionarioDto funcionario) {

        // valida se empresa existe
        Integer empresaId = funcionario.getIdEmpresa();
        Optional<PessoaJuridica> optionalEmpresa = empresaRepository.findById(empresaId);
        if (!optionalEmpresa.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro());
        }

        // valida se funcionario existe
        Integer funcionarioId = funcionario.getIdPessoa();
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
    public boolean excluirFuncionario(Integer id) {
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
                    EnumErrosCadastroFuncionario.EMPRESA_NAO_ENCONTRADA.getMensagemErro() + funcionario.getIdEmpresa());
        }

        // Verifica se o email já está cadastrado
        String email = encryptor.encrypt(funcionario.getEmail());
        Optional<PessoaFisica> optionalFuncionarioByEmail = cadastroFuncionariosRepository.findByEmail(email);
        if (optionalFuncionarioByEmail.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Email já cadastrado");
        }

        // Verifica se o nome já está cadastrado
        String nome = encryptor.encrypt(funcionario.getNome());
        Optional<PessoaFisica> optionalFuncionarioByNome = cadastroFuncionariosRepository.findByNome(nome);
        if (optionalFuncionarioByNome.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "Nome já cadastrado");
        }

        // Verifica se o RG já está cadastrado
        String rg = encryptor.encrypt(funcionario.getRg());
        Optional<PessoaFisica> optionalFuncionarioByRG = cadastroFuncionariosRepository.findByRg(rg);
        if (optionalFuncionarioByRG.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "RG já cadastrado");
        }

        // Verifica se o CPF já está cadastrado
        String cpf = encryptor.encrypt(funcionario.getCpf());
        Optional<PessoaFisica> optionalFuncionarioByCpf = cadastroFuncionariosRepository.findByCpf(cpf);
        if (optionalFuncionarioByCpf.isPresent()) {
            throw new ErrosSistema.FuncionarioException(
                    "CPF já cadastrado");
        }

        // Verifica se o telefone já está cadastrado
        String ddd = encryptor.encrypt(funcionario.getTelefone().getDdd());
        String telefone = encryptor.encrypt(funcionario.getTelefone().getNumero());
        Optional<Telefone> optionalTelefone = telefoneRepository
                .findByDddAndNumero(ddd, telefone);
        if (optionalTelefone.isPresent()) {
            throw new ErrosSistema.EmpresaException(
                    "Telefone já cadastrado.");
        }
    }

    private void setFuncionarioFields(PessoaFisica novoFuncionario, FuncionarioDto funcionario) {
        
        //Nome - RG cpf - dataNascimento - email
        novoFuncionario.setNome(encryptor.encrypt(funcionario.getNome()));
        novoFuncionario.setRg(encryptor.encrypt(funcionario.getRg()));
        novoFuncionario.setCpf(encryptor.encrypt(funcionario.getCpf()));
        novoFuncionario.setDataNascimento(encryptor.encrypt(funcionario.getDataNascimento()));
        novoFuncionario.setEmail(encryptor.encrypt(funcionario.getEmail()));

        
        //folha pagamento
        FolhaPagamento folhaPagamento = new FolhaPagamento();
        folhaPagamento.setTipoContrato(funcionario.getFolhaPagamento().getTipoContrato());
        folhaPagamento.setDataAdmissao(funcionario.getFolhaPagamento().getDataAdmissao());
        folhaPagamento.setNumeroFilhos(funcionario.getFolhaPagamento().getNumeroFilhos());
        folhaPagamento.setSalario(funcionario.getFolhaPagamento().getSalario());
        novoFuncionario.setFolhaPagamento(folhaPagamento);

        //banco horas - departamento - cargo
        novoFuncionario.setBancoHoras(new BancoHorasEntity());
        novoFuncionario.getBancoHoras().setDataInicioApuracao(new Date());
        novoFuncionario.getBancoHoras().setIdSaldoHoras(6);
        novoFuncionario.setIdDepartamento(funcionario.getIdDepartamento());
        novoFuncionario.setIdCargo(funcionario.getIdCargo());

        // saldoHoras
        novoFuncionario.getBancoHoras().setSaldoHoras(new SaldoHorasEntity());

        //jornadaTrabalho
        novoFuncionario.getBancoHoras().setJornadaTrabalho(new JornadaTrabalhoEntity());
        novoFuncionario.getBancoHoras().getJornadaTrabalho().setGeolocalizacaoPermitida(funcionario.getJornadaTrabalho().getGeolocalizacaoPermitida());
        novoFuncionario.getBancoHoras().getJornadaTrabalho().setJornadaTrabalhoHoras(funcionario.getJornadaTrabalho().getJornadaTrabalhoHoras());
        novoFuncionario.getBancoHoras().getJornadaTrabalho().setHorarioEntrada(funcionario.getJornadaTrabalho().getHorarioEntrada());
        novoFuncionario.getBancoHoras().getJornadaTrabalho().setHorarioSaida(funcionario.getJornadaTrabalho().getHorarioSaida());
        novoFuncionario.getBancoHoras().getJornadaTrabalho().setIntervaloDescanso(funcionario.getJornadaTrabalho().getIntervaloDescanso());

        //Telefone
        novoFuncionario.setTelefone(new Telefone());
        novoFuncionario.getTelefone().setDdd(encryptor.encrypt(funcionario.getTelefone().getDdd()));
        novoFuncionario.getTelefone().setNumero(encryptor.encrypt(funcionario.getTelefone().getNumero()));
        
        //Login
        novoFuncionario.setLogin(new Login()); 
        novoFuncionario.getLogin().setSenhaUsuario(encryptor.encrypt(funcionario.getSenha()));
        novoFuncionario.getLogin().setEmail(encryptor.encrypt(funcionario.getEmail()));
    
        //endereco
        novoFuncionario.setEndereco(new Endereco());
        novoFuncionario.getEndereco().setLogradouro(encryptor.encrypt(funcionario.getEndereco().getLogradouro()));
        novoFuncionario.getEndereco().setNumero(encryptor.encrypt(funcionario.getEndereco().getNumero()));
        novoFuncionario.getEndereco().setComplemento(encryptor.encrypt(funcionario.getEndereco().getComplemento()));
        novoFuncionario.getEndereco().setBairro(encryptor.encrypt(funcionario.getEndereco().getBairro()));
        novoFuncionario.getEndereco().setCidade(encryptor.encrypt(funcionario.getEndereco().getCidade()));
        novoFuncionario.getEndereco().setEstado(encryptor.encrypt(funcionario.getEndereco().getEstado()));
        novoFuncionario.getEndereco().setCep(encryptor.encrypt(funcionario.getEndereco().getCep()));

    }
    
    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}
