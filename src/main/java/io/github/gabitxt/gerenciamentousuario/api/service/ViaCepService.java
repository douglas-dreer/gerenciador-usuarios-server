package io.github.gabitxt.gerenciamentousuario.api.service;

import io.github.gabitxt.gerenciamentousuario.api.client.ViaCepClient;
import io.github.gabitxt.gerenciamentousuario.api.dto.EnderecoApiResponse;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ViaCepService {
    private final ViaCepClient client;

    public ViaCepService(ViaCepClient client) {
        this.client = client;
    }

    /**
     * Busca um endereço pelo CEP utilizando o serviço ViaCep.
     * @param cep o CEP do endereço a ser buscado.
     * @return o EnderecoDTO correspondente ao CEP fornecido.
     */
    public EnderecoDTO buscarEnderecoPorCep(@NotNull @NotBlank String cep) {
        EnderecoApiResponse resultado = client.buscarEnderecoPorCep(cep);
        return resultado.toDomain();
    }
}
