package com.cit.virtual_ponto.cadastro_funcionarios.dto;

public class LoginRequestDto {

    private String email;
    private String senha;

    // Construtor vazio (obrigatório para o Spring deserializar o JSON)
    public LoginRequestDto() {
    }

    // Construtor com os campos
    public LoginRequestDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
