package io.github.gabitxt.gerenciamentousuario.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * BuscarEnderecoPorTermo
 * Busca endereços com base em um termo específico dentro de uma cidade e estado fornecidos.
 *
 * @param cep Código Postal
 * @param estado Estado
 * @param cidade Cidade
 * @param termo Termo de busca
 */

@Schema(description = "Parâmetros para busca de endereços por termo")
public record BuscarEnderecoPorTermoRequest(
        @Schema(description = "Sigla do estado (UF)", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @NotBlank
        String estado,

        @Schema(description = "Nome da cidade", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @NotBlank
        String cidade,

        @Schema(description = "Termo de busca (logradouro)", example = "Paulista", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @NotBlank
        String termo
) {}
