package com.cit.virtual_ponto.cadastro_funcionarios.models.pessoa;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login", nullable = false)
    private Integer idLogin;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha_usuario", nullable = false)
    private String senhaUsuario;

    @Column(name = "id_historico_login")
    private Integer idHistoricoLogin;
}
