package io.github.gabitxt.gerenciamentousuario.model;

import lombok.Builder;

/**
 * DTO para transferência de dados de Endereço.
 * Imutável - todos os campos são final.
 */
@Builder(toBuilder = true)
public record EnderecoDTO(
        Long id,
        Integer numero,
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String estado,
        String regiao
) {
    /**
     * Builder com valor default para numero.
     */
    public static class EnderecoDTOBuilder {
        private Integer numero = 0;
    }
}
