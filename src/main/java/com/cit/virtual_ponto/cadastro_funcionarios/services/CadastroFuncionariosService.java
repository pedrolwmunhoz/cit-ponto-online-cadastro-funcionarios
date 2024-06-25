package com.cit.virtual_ponto.cadastro_funcionarios.services;

import com.cit.virtual_ponto.cadastro_funcionarios.models.cadastro_funcionarios.CadastroFuncionariosEntity;
import com.cit.virtual_ponto.cadastro_funcionarios.repositories.CadastroFuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroFuncionariosService {

    private final CadastroFuncionariosRepository cadastroFuncionariosRepository;

    @Autowired
    public CadastroFuncionariosService(CadastroFuncionariosRepository cadastroFuncionariosRepository) {
        this.cadastroFuncionariosRepository = cadastroFuncionariosRepository;
    }

    public CadastroFuncionariosEntity cadastrarFuncionario(CadastroFuncionariosEntity funcionario) {
        return cadastroFuncionariosRepository.save(funcionario);
    }

    public CadastroFuncionariosEntity atualizarFuncionario(Long id, CadastroFuncionariosEntity funcionario) {
        Optional<CadastroFuncionariosEntity> optionalFuncionario = cadastroFuncionariosRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            CadastroFuncionariosEntity funcionarioExistente = optionalFuncionario.get();
            funcionarioExistente.setNome(funcionario.getNome());
            funcionarioExistente.setCpf(funcionario.getCpf());
            funcionarioExistente.setEmail(funcionario.getEmail());
            funcionarioExistente.setTelefone(funcionario.getTelefone());
            funcionarioExistente.setEmpresaId(funcionario.getEmpresaId());

            return cadastroFuncionariosRepository.save(funcionarioExistente);
        } else {
            return null;
        }
    }

    public boolean excluirFuncionario(Long id) {
        if (cadastroFuncionariosRepository.existsById(id)) {
            cadastroFuncionariosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<CadastroFuncionariosEntity> listarFuncionarios() {
        return cadastroFuncionariosRepository.findAll();
    }

    public List<CadastroFuncionariosEntity> buscarFuncionariosPorNome(String nome) {
        return cadastroFuncionariosRepository.findByNomeContainingIgnoreCase(nome);
    }
}
