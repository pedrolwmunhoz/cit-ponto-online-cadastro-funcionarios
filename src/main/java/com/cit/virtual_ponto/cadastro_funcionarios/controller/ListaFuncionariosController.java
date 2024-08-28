package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.models.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.services.CadastroFuncionariosService;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ListarFuncionariosService;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ValidaLoginFuncionarioService;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ListaFuncionariosController {

    private final CadastroFuncionariosService funcionariosService;
    private final ListarFuncionariosService listarFuncionariosService;
    private final ValidaLoginFuncionarioService validaLoginFuncionarioService;
    

    @Autowired
    public ListaFuncionariosController(FuncionariosService funcionariosService,
    ListarFuncionariosService listarFuncionariosService, ValidaLoginFuncionarioService validaLoginFuncionarioService) {
        this.funcionariosService = funcionariosService;
        this.listarFuncionariosService = listarFuncionariosService;
        this.validaLoginFuncionarioService = validaLoginFuncionarioService;
    }
    
    @GetMapping("/listar-funcionarios")
    public ResponseEntity<List<PessoaFisica>> listarFuncionarios() {
        List<PessoaFisica> funcionarios = listarFuncionariosService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<List<PessoaFisica>> buscarFuncionariosPorNome(
            @RequestParam @NotBlank(message = "O nome não pode ser vazio") @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres") String nome) {
        List<PessoaFisica> funcionarios = listarFuncionariosService.buscarFuncionariosPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PessoaFisica> buscarFuncionarioPorId(
            @PathVariable @Min(value = 1, message = "O ID do funcionário deve ser um valor positivo") Long id) {
        return ResponseEntity.ok(listarFuncionariosService.buscarFuncionarioPorId(id));
    }
}
