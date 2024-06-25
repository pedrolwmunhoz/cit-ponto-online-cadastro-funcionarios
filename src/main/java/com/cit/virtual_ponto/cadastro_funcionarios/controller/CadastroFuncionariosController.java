package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.models.cadastro_funcionarios.CadastroFuncionariosEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.services.CadastroFuncionariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CadastroFuncionariosController {

    private final CadastroFuncionariosService funcionariosService;

    @Autowired
    public CadastroFuncionariosController(CadastroFuncionariosService funcionariosService) {
        this.funcionariosService = funcionariosService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CadastroFuncionariosEntity> cadastrarFuncionario(@RequestBody CadastroFuncionariosEntity funcionario) {
        CadastroFuncionariosEntity novoFuncionario = funcionariosService.cadastrarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CadastroFuncionariosEntity> atualizarFuncionario(
            @PathVariable Long id, @RequestBody CadastroFuncionariosEntity funcionario) {
        CadastroFuncionariosEntity funcionarioAtualizado = funcionariosService.atualizarFuncionario(id, funcionario);
        if (funcionarioAtualizado != null) {
            return ResponseEntity.ok(funcionarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Long id) {
        boolean removido = funcionariosService.excluirFuncionario(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CadastroFuncionariosEntity>> listarFuncionarios() {
        List<CadastroFuncionariosEntity> funcionarios = funcionariosService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CadastroFuncionariosEntity>> buscarFuncionariosPorNome(@RequestParam String nome) {
        List<CadastroFuncionariosEntity> funcionarios = funcionariosService.buscarFuncionariosPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }
}
