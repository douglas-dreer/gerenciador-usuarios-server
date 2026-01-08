package io.github.gabitxt.gerenciamentousuario.api.dto;

import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;

/**
 * Classe que representa a resposta da API de endereço.
 * 
 * @param cep CEP do endereço.
 * @param logradouro Logradouro do endereço.
 * @param complemento Complemento do endereço.
 * @param unidade Unidade do endereço.
 * @param bairro Bairro do endereço.
 * @param localidade Localidade do endereço.
 * @param uf Unidade Federativa do endereço.
 * @param estado Estado do endereço.
 * @param regiao Região do endereço.
 * @param ibge IBGE do endereço.
 * @param gia GIA do endereço.
 * @param ddd DDD do endereço.
 * @param siafi SIAFI do endereço.
 */
public record EnderecoApiResponse(
    String cep,
    String logradouro,
    String complemento,
    String unidade,
    String bairro,
    String localidade,
    String uf,
    String estado,
    String regiao,
    Long ibge,
    Integer gia,
    Integer ddd,
    Integer siafi
) {
    public EnderecoDTO toDomain() {
        return EnderecoDTO.builder()
                .cep(this.cep)
                .logradouro(this.logradouro)
                .complemento(this.complemento)
                .bairro(this.bairro)
                .localidade(this.localidade)
                .estado(this.estado)
                .regiao(this.regiao)
                .build();
    }
}