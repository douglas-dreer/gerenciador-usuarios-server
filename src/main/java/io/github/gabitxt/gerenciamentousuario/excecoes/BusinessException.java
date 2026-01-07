package io.github.gabitxt.gerenciamentousuario.excecoes;

public class BusinessException extends RuntimeException {

    public BusinessException (String message ) {
        super (message);

    }

    public BusinessException (String message,  Throwable cause ){

        super (message, cause );
    }
}
