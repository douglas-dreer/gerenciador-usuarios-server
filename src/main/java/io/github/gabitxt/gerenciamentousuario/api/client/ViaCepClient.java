package io.github.gabitxt.gerenciamentousuario.api.client;

import io.github.gabitxt.gerenciamentousuario.api.dto.EnderecoApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "viacep-client", url = "${viacep.url}")
public interface ViaCepClient {
    /**
     * Busca um endereço pelo CEP utilizando o serviço ViaCep.
     *
     * @param cep o CEP do endereço a ser buscado.
     * @return o EnderecoApiResponse correspondente ao CEP fornecido.
     */
    @GetMapping("/{cep}/json/")
    EnderecoApiResponse buscarEnderecoPorCep(@PathVariable("cep") String cep);

    /**
     * Busca endereços por logradouro, localidade e unidade federativa (UF) utilizando o serviço ViaCep.
     * @param uf
     * @param localidade
     * @param logradouro
     * @return
     */
    @GetMapping("/{uf}/{localidade}/{logradouro}/json/")
    List<EnderecoApiResponse> buscarEnderecosPorLogradouro(
            @PathVariable("uf") String uf,
            @PathVariable("localidade") String localidade,
            @PathVariable("logradouro") String logradouro
    );
}
