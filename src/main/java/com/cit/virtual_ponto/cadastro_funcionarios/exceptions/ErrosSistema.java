package com.cit.virtual_ponto.cadastro_funcionarios.exceptions;


public class ErrosSistema {

    public static final class EmpresaException extends RuntimeException {
        public EmpresaException(String message) {
            super(message);
        }
    }

    public static final class FuncionarioException extends RuntimeException {
        public FuncionarioException(String message) {
            super(message);
        }
    }
    
    public static final class DatabaseException extends RuntimeException {
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}