package io.github.gabitxt.gerenciamentousuario.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarEnderecoRequest(
        @NotNull 
        @NotBlank
        String cep,
        
        Integer numero,
        String complemento) {
}
