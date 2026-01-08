package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.controller.request.BuscarEnderecoPorTermoRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarEnderecoRequest;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;

import java.util.List;

/**
 * Interface para o serviço de endereço.
 */
public interface EnderecoService {
    /**
     * Cria um novo endereço.
     * 
     * @param request
     * @return Endereco
     */
    EnderecoDTO criarEndereco(CriarEnderecoRequest request);
    /**
     * Busca um endereço por ID.
     * 
     * @param id
     * @return Endereco
     */
    EnderecoDTO buscarEnderecoPorId(Long id);

    /**
    * Busca endereços por estado, cidade e termo.
     * @param request params
    */
    List<EnderecoDTO> obterEnderecoPorTermo(BuscarEnderecoPorTermoRequest parametros);
    /**
     * Atualiza um endereço.
     * 
     * @param id
     * @param request
     * @return Endereco
     */
    EnderecoDTO atualizarEndereco(Long id, CriarEnderecoRequest request);
    /**
     * Deleta um endereço.
     * 
     * @param id
     */
    void deletarEndereco(Long id);
}