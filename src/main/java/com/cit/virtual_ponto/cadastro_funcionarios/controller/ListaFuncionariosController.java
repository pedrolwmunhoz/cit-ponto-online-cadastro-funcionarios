package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa.PessoaFisica;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ListarFuncionariosService;

import jakarta.validation.constraints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ListaFuncionariosController {

    private final ListarFuncionariosService listarFuncionariosService;
    
    @Autowired
    public ListaFuncionariosController(
    ListarFuncionariosService listarFuncionariosService) {
        this.listarFuncionariosService = listarFuncionariosService;
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
            @PathVariable @Min(value = 1, message = "O ID do funcionário deve ser um valor positivo") Integer id) {
        return ResponseEntity.ok(listarFuncionariosService.buscarFuncionarioPorId(id));
    }
}
