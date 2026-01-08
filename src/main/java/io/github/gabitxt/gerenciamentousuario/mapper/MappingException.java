package io.github.gabitxt.gerenciamentousuario.mapper;

/**
 * Exceção específica para erros de mapeamento.
 * Segue SRP - responsabilidade única de representar erros de mapping.
 */
public class MappingException extends RuntimeException {

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}

