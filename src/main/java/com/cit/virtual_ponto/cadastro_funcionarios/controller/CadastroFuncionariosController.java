package com.cit.virtual_ponto.cadastro_funcionarios.controller;

import com.cit.virtual_ponto.cadastro_funcionarios.dto.FuncionarioDto;
import com.cit.virtual_ponto.cadastro_funcionarios.dto.LoginRequestDto;
import com.cit.virtual_ponto.cadastro_funcionarios.models.FuncionarioEntity;
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

@RestController
@RequestMapping("/")
@Validated
public class CadastroFuncionariosController {

    private final CadastroFuncionariosService funcionariosService;
    private final ListarFuncionariosService listarFuncionariosService;
    private final ValidaLoginFuncionarioService validaLoginFuncionarioService;
    

    @Autowired
    public CadastroFuncionariosController(CadastroFuncionariosService funcionariosService,
            ListarFuncionariosService listarFuncionariosService, ValidaLoginFuncionarioService validaLoginFuncionarioService) {
        this.funcionariosService = funcionariosService;
        this.listarFuncionariosService = listarFuncionariosService;
        this.validaLoginFuncionarioService = validaLoginFuncionarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioEntity> cadastrarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto ) {
        FuncionarioEntity novoFuncionario = funcionariosService.cadastrarFuncionario(funcionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<FuncionarioEntity> atualizarFuncionario(@RequestBody @Valid FuncionarioDto funcionario) {
        FuncionarioEntity funcionarioAtualizado = funcionariosService.atualizarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioAtualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirFuncionario(
            @PathVariable @Min(value = 1, message = "O ID do funcionário deve ser um valor positivo") Long id) {
        funcionariosService.excluirFuncionario(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/listar-funcionarios")
    public ResponseEntity<List<FuncionarioEntity>> listarFuncionarios() {
        List<FuncionarioEntity> funcionarios = listarFuncionariosService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<List<FuncionarioEntity>> buscarFuncionariosPorNome(
            @RequestParam @NotBlank(message = "O nome não pode ser vazio") @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres") String nome) {
        List<FuncionarioEntity> funcionarios = listarFuncionariosService.buscarFuncionariosPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<FuncionarioEntity> buscarFuncionarioPorId(
            @PathVariable @Min(value = 1, message = "O ID do funcionário deve ser um valor positivo") Long id) {
        return ResponseEntity.ok(listarFuncionariosService.buscarFuncionarioPorId(id));
    }

    @PostMapping("/validar-login")
    public ResponseEntity<FuncionarioEntity> validarLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(validaLoginFuncionarioService.validarLogin(loginRequestDto));
    }

}
