package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.EnderecoDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.FolhaPagamentoDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.JornadaTrabalhoDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.TelefoneDto;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.services.CadastroFuncionariosService;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ValidaLoginFuncionarioService;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@Validated
public class CadastroFuncionariosController {

    private final CadastroFuncionariosService funcionariosService;
    private final ValidaLoginFuncionarioService validaLoginFuncionarioService;
    

    @Autowired
    public CadastroFuncionariosController(CadastroFuncionariosService funcionariosService, ValidaLoginFuncionarioService validaLoginFuncionarioService) {
        
        this.funcionariosService = funcionariosService;
        this.validaLoginFuncionarioService = validaLoginFuncionarioService;
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<PessoaFisica> cadastrarFuncionario( ) {
        FuncionarioDto novoFuncionario = new FuncionarioDto();

        // Configuração do ID do funcionário
        novoFuncionario.setIdFuncionario(1);

        // Configuração do CPF e RG
        novoFuncionario.setCpf("12345678901");
        novoFuncionario.setRg("123456789");

        // Configuração da data de nascimento
        novoFuncionario.setDataNascimento("1990-01-01");

        // Configuração dos IDs de empresa, departamento e cargo
        novoFuncionario.setIdEmpresa(1);
        novoFuncionario.setNome("Pedro Munhoz");
        novoFuncionario.setIdDepartamento(1);
        novoFuncionario.setIdCargo(1);
        novoFuncionario.setEmail("empresa@example.com");
        novoFuncionario.setEmail("empresa@example.com");
        novoFuncionario.setSenha("senha123");

        // Configuração do telefone
        TelefoneDto telefone = new TelefoneDto();
        telefone.setDdd("11");
        telefone.setNumero("987654321");
        novoFuncionario.setTelefone(telefone);

        // Configuração do login
        LoginDto login = new LoginDto();
        login.setEmail("empresa@example.com");
        login.setSenha("senha123");
        novoFuncionario.setLogin(login);

        // Configuração do endereço
        EnderecoDto endereco = new EnderecoDto();
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("100");
        endereco.setComplemento("Apto 202");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("12345-678");
        novoFuncionario.setEndereco(endereco);

        // Configuração da folha de pagamento
        FolhaPagamentoDto folhaPagamento = new FolhaPagamentoDto();
        folhaPagamento.setIdFolhaPagamento(1);
        folhaPagamento.setTipoContrato("CLT");
        folhaPagamento.setDataAdmissao(new Date());
        folhaPagamento.setNumeroFilhos(2);
        folhaPagamento.setSalario(new BigDecimal("5000.00"));
        novoFuncionario.setFolhaPagamento(folhaPagamento);

        // Configuração da jornada de trabalho
        JornadaTrabalhoDto jornadaTrabalho = new JornadaTrabalhoDto();
        jornadaTrabalho.setIdJornadaTrabalho(1);
        jornadaTrabalho.setGeolocalizacaoPermitida("São Paulo, SP");
        jornadaTrabalho.setJornadaTrabalhoHoras(LocalTime.of(8, 0));
        jornadaTrabalho.setHorarioEntrada(LocalTime.of(9, 0));
        jornadaTrabalho.setHorarioSaida(LocalTime.of(18, 0));
        jornadaTrabalho.setIntervaloDescanso(LocalTime.of(12, 0));
        novoFuncionario.setJornadaTrabalho(jornadaTrabalho);

        PessoaFisica funcionario = funcionariosService.cadastrarFuncionario(novoFuncionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionario);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PessoaFisica> atualizarFuncionario(@RequestBody @Valid FuncionarioDto funcionario) {
        PessoaFisica funcionarioAtualizado = funcionariosService.atualizarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioAtualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirFuncionario(
            @PathVariable @Min(value = 1, message = "O ID do funcionário deve ser um valor positivo") Integer id) {
        funcionariosService.excluirFuncionario(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/validar-login")
    public ResponseEntity<PessoaFisica> validarLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(validaLoginFuncionarioService.validarLogin(loginRequestDto));
    }

}
