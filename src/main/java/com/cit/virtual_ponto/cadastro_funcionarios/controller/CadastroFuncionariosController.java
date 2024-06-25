package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.services.CadastroFuncionariosService;
import com.cit.virtual_ponto.cadastro_funcionarios.services.ListarFuncionariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CadastroFuncionariosController {

    private final CadastroFuncionariosService funcionariosService;
    private final ListarFuncionariosService listarFuncionariosService;

    @Autowired
    public CadastroFuncionariosController(CadastroFuncionariosService funcionariosService, ListarFuncionariosService listarFuncionariosService) {
        this.funcionariosService = funcionariosService;
        this.listarFuncionariosService = listarFuncionariosService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioEntity> cadastrarFuncionario(@RequestBody FuncionarioEntity funcionario) {
        FuncionarioEntity novoFuncionario = funcionariosService.cadastrarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<FuncionarioEntity> atualizarFuncionario(@RequestBody FuncionarioEntity funcionario) {
        FuncionarioEntity funcionarioAtualizado = funcionariosService.atualizarFuncionario(funcionario);
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

    @GetMapping("/listar-funcionarios")
    public ResponseEntity<List<FuncionarioEntity>> listarFuncionarios() {
        List<FuncionarioEntity> funcionarios = listarFuncionariosService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<List<FuncionarioEntity>> buscarFuncionariosPorNome(@RequestParam String nome) {
        List<FuncionarioEntity> funcionarios = listarFuncionariosService.buscarFuncionariosPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<FuncionarioEntity> buscarFuncionarioPorId(@PathVariable Long id) {
        Optional<FuncionarioEntity> funcionario = listarFuncionariosService.buscarFuncionarioPorId(id);
        if (funcionario.isPresent()) {
            return ResponseEntity.ok(funcionario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
