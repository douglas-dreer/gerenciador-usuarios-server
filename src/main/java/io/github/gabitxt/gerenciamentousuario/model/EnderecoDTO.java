package io.github.gabitxt.gerenciamentousuario.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO para transferência de dados de Endereço.
 * Imutável - todos os campos são final.
 */
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados do endereço")
public record EnderecoDTO(
        @Schema(description = "ID do endereço", example = "1")
        Long id,

        @Schema(description = "Número do logradouro", example = "123")
        Integer numero,

        @Schema(description = "CEP do endereço", example = "01310-100")
        String cep,

        @Schema(description = "Nome do logradouro", example = "Avenida Paulista")
        String logradouro,

        @Schema(description = "Complemento do endereço", example = "Apto 45")
        String complemento,

        @Schema(description = "Nome do bairro", example = "Bela Vista")
        String bairro,

        @Schema(description = "Nome da cidade", example = "São Paulo")
        String localidade,

        @Schema(description = "Sigla do estado (UF)", example = "SP")
        String estado,

        @Schema(description = "Região do Brasil", example = "Sudeste")
        String regiao
) {
    /**
     * Builder com valor default para numero.
     */
    public static class EnderecoDTOBuilder {
        private Integer numero = 0;
    }
}
