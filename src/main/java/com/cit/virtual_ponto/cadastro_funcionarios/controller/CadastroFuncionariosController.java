package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.services.CadastroFuncionariosService;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ValidaLoginFuncionarioService;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

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

    @PostMapping("/cadastrar")
    public ResponseEntity<PessoaFisica> cadastrarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto ) {
        PessoaFisica novoFuncionario = funcionariosService.cadastrarFuncionario(funcionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
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
