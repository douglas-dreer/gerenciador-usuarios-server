package io.github.gabitxt.gerenciamentousuario.globalizacao;

import lombok.Getter;

import java.util.List;
import java.util.ArrayList;



// classe que vai conter uma lista de erro
@Getter
public class ErrorReasponse {
    private List<Error> errors;

    public ErrorReasponse () {
        this.errors = new ArrayList<>();

    }
    public void addError( Error error ){
        this.errors.add(error);
    }

}
