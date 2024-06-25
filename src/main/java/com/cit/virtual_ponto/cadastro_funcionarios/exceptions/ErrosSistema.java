package com.cit.virtual_ponto.cadastro_funcionarios.exceptions;


public class ErrosSistema {

    public static final class EmpresaNotFoundException extends RuntimeException {
        public EmpresaNotFoundException(String message) {
            super(message);
        }
    }

    public static final class FuncionarioNotFoundException extends RuntimeException {
        public FuncionarioNotFoundException(String message) {
            super(message);
        }
    }
    
    public static final class DatabaseException extends RuntimeException {
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}