package io.github.gabitxt.gerenciamentousuario.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de um novo endereço")
public record CriarEnderecoRequest(
        @Schema(description = "CEP do endereço (8 dígitos)", example = "01310100", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String cep,

        @Schema(description = "Número do logradouro", example = "123")
        Integer numero,

        @Schema(description = "Complemento do endereço", example = "Apto 45")
        String complemento
) {}
